package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.common.Steppable
import context.EntityRepositoryContext
import org.aas.sbtanks.event.EventSource

trait RemovableController:
    def onRemoved(): Unit

/**
 * Adds the ability to register controller factories, to automatically create controllers from models
 *
 * @param context The context that will be passed to the controller factories
 */
trait EntityControllerRepository[Model, View, Context <: EntityRepositoryContext[?, ?, ?]](using context: Context) extends Steppable:
    this: EntityMvRepositoryContainer[Model, View] =>

    type Controller = Steppable

    private case class ControllerFactory[ProviderParams](validModelPredicate: Model => Boolean, provider: ProviderParams => Controller)
    private type ControllerWithViewFactory = ControllerFactory[(Context, Model, View)]
    private type ControllerWithoutViewFactory = ControllerFactory[(Context, Model)]

    private var controllers = Seq.empty[(Option[Model], Controller)]
    private var controllerWithViewFactories = Seq.empty[ControllerWithViewFactory]
    private var controllerWithoutViewFactories = Seq.empty[ControllerWithoutViewFactory]

    modelViewAdded += { (m, v) => createMvController(m, v) }
    modelViewRemoved += { (m, _) => removeMvController(m) }

    /**
     * @inheritDoc
     */
    override def step(delta: Double): this.type =
        controllers = controllers map { c => (c(0), c(1).step(delta)) }
        this

    /**
     * Registers a controller factory to this repository for models that have a view associated with them
     *
     * @param validPredicate The predicate to filter which models will use this factory
     * @param factory The controller factory method
     * @return This repository
     */
    def registerControllerFactory[M <: Model, V <: View](validPredicate: Model => Boolean, factory: (Context, M, V) => Controller): this.type =
        controllerWithViewFactories = controllerWithViewFactories :+ ControllerFactory(validPredicate, (c, m, v) => factory(c, m.asInstanceOf[M], v.asInstanceOf[V]))
        this

    /**
     * Registers a controller factory to this repository for models that don't have a view associated with them
     *
     * @param validPredicate The predicate to filter which models will use this factory
     * @param factory The controller factory method
     * @return This repository
     */
    def registerControllerFactory[M <: Model](validPredicate: Model => Boolean, factory: (Context, M) => Controller): this.type =
        controllerWithoutViewFactories = controllerWithoutViewFactories :+ ControllerFactory(validPredicate, (c, m) => factory(c, m.asInstanceOf[M]))
        this

    /**
     * Manually adds a controller to this repository
     *
     * @param controller The controller to add
     * @return This repository
     */
    def addController(controller: Controller): this.type = queueCommand { () =>
        controllers = controllers :+ (Option.empty, controller)
    }

    /**
     * Manually removes a controller to this repository. Can remove both manually and automatically added controllers
     *
     * @param controller The controller to add
     * @return This repository
     */
    def removeController(controller: Controller): this.type = queueCommand { () =>
        onControllerRemoved(controller)
    }

    /**
     * Gets the total amount of controllers in this repository
     *
     * @return The controllers count
     */
    def controllerCount = controllers.size

    /**
     * Gets the total amount of controller factories in this repository
     *
     * @return The controller factories count
     */
    def controllerFactoryCount = controllerWithViewFactories.size + controllerWithoutViewFactories.size

    protected def createMvController(model: Model, view: Option[View]): this.type = queueCommand { () =>
        val controller = view match
            case None => controllerWithoutViewFactories.find(factory => factory.validModelPredicate(model))
                .map(factory => factory.provider) match
                case None => Option.empty
                case Some(provider) => Option(provider(context, model))
            case Some(v) => controllerWithViewFactories.find(factory => factory.validModelPredicate(model))
                .map(factory => factory.provider) match
                case None => Option.empty
                case Some(provider) => Option(provider(context, model, v))
        controller match
            case None => this
            case Some(c) =>
                controllers = controllers :+ ((Option(model), c))
    }

    protected def removeMvController(model: Model): this.type = queueCommand { () =>
        controllers.find(c => c(0).contains(model)) match
            case None => this
            case Some(c) =>
                controllers = controllers.filterNot(c => c(0).contains(model))
    }

    protected def editControllers(modifier: (Option[Model], Controller) => Controller) = queueCommand { () =>
        controllers = controllers.map(c => (c(0), modifier(c(0), c(1))))
    }

    protected def onControllerRemoved(controller: Controller): Unit =
        controllers = controllers.filterNot(c => c(1) == controller)
        if controller.isInstanceOf[RemovableController] then
            controller.asInstanceOf[RemovableController].onRemoved()