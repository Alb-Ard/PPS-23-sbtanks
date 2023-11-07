package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.common.Steppable

/**
  * Adds the ability to (un)register controller factories, to automatically create controllers from models
  *
  * @param context The context that will be passed to the controller factories
  */
trait EntityControllerRepository[Model, View, Context <: EntityRepositoryContext[?]](using context: Context) extends Steppable:
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

    override def step(delta: Double): this.type =
        controllers = controllers map { c => (c(0), c(1).step(delta)) }
        this

    def registerControllerFactory[M <: Model, V <: View](validPredicate: Model => Boolean, factory: (Context, M, V) => Controller): this.type =
        controllerWithViewFactories = controllerWithViewFactories :+ ControllerFactory(validPredicate, (c, m, v) => factory(c, m.asInstanceOf[M], v.asInstanceOf[V]))
        this

    def registerControllerFactory[M <: Model](validPredicate: Model => Boolean, factory: (Context, M) => Controller): this.type =
        controllerWithoutViewFactories = controllerWithoutViewFactories :+ ControllerFactory(validPredicate, (c, m) => factory(c, m.asInstanceOf[M]))
        this

    def addController(controller: Controller): this.type =
        controllers = controllers :+ (Option.empty, controller)
        this

    def removeController(controller: Controller): this.type =
        controllers = controllers.filterNot(c => c(1) == controller)
        this

    def controllerCount = controllers.size
    
    def controllerFactoryCount = controllerWithViewFactories.size + controllerWithoutViewFactories.size

    protected def createMvController(model: Model, view: Option[View]): this.type =
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
                this

    protected def removeMvController(model: Model): this.type =
        controllers.find(c => c(0) == model) match
            case None => this
            case Some(c) =>
                controllers = controllers.filterNot(c => c(0) == model)
                this

    protected def editControllers(modifier: (Option[Model], Controller) => Controller) =
        controllers = controllers.map(c => (c(0), modifier(c(0), c(1))))

object EntityControllerRepository:
    extension [Model, View, Context <: EntityRepositoryContext[?]](controllerRepository: EntityControllerRepository[Model, View, Context])
        def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: (Context, M, V) => EntityControllerRepository[Model, View, Context]#Controller) =
            controllerRepository.registerControllerFactory(modelType.isInstance, factory)

        def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: (Context, M) => EntityControllerRepository[Model, View, Context]#Controller) =
            controllerRepository.registerControllerFactory(modelType.isInstance, factory)