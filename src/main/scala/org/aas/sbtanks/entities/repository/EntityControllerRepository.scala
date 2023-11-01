package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.common.Steppable

trait EntityControllerRepository[Model, View, Context <: EntityRepositoryContext[?]](using context: Context) extends Steppable:
    this: EntityMvRepositoryContainer[Model, View] =>

    type Controller = Steppable

    private case class ControllerWithViewFactory(validModelPredicate: Model => Boolean, provider: (Context, Model, View) => Controller)
    private case class ControllerWithoutViewFactory(validModelPredicate: Model => Boolean, provider: (Context, Model) => Controller)

    private var controllers = Seq.empty[Controller]
    private var modelControllers = Map.empty[Model, Controller]
    private var controllerWithViewFactories = Seq.empty[ControllerWithViewFactory]
    private var controllerWithoutViewFactories = Seq.empty[ControllerWithoutViewFactory]

    modelViewAdded += { (m, v) => createMvController(m, Option(v)) }
    modelAdded += { m => createMvController(m, Option.empty) }

    modelViewRemoved += { (m, _) => removeMvController(m) }
    modelRemoved += removeMvController

    override def step(delta: Double): this.type =
        controllers = controllers map { c => c.step(delta) }
        this

    def registerControllerFactory[M <: Model, V <: View](validPredicate: Model => Boolean, factory: (Context, M, V) => Controller): this.type =
        controllerWithViewFactories = controllerWithViewFactories :+ ControllerWithViewFactory(validPredicate, (c, m, v) => factory(c, m.asInstanceOf[M], v.asInstanceOf[V]))
        this

    def registerControllerFactory[M <: Model, V <: View](validPredicate: Model => Boolean, factory: (Context, M) => Controller): this.type =
        controllerWithoutViewFactories = controllerWithoutViewFactories :+ ControllerWithoutViewFactory(validPredicate, (c, m) => factory(c, m.asInstanceOf[M]))
        this

    def addController(controller: Controller): this.type =
        controllers = controllers :+ controller
        this

    def removeController(controller: Controller): this.type =
        controllers = controllers.filterNot(controller.equals)
        this

    private def createMvController(model: Model, view: Option[View]): this.type =
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
                modelControllers = modelControllers + ((model, c))
                addController(c)

    private def removeMvController(model: Model): this.type =
        modelControllers.get(model) match
            case None => this
            case Some(controller) =>
                modelControllers = modelControllers - model
                removeController(controller)

object EntityControllerRepository:
    extension [Model, View, Context <: EntityRepositoryContext[?]](controllerRepository: EntityControllerRepository[Model, View, Context])
        def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: (Context, M, V) => EntityControllerRepository[Model, View, Context]#Controller) =
            controllerRepository.registerControllerFactory(modelType.isInstance, factory)

        def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: (Context, M) => EntityControllerRepository[Model, View, Context]#Controller) =
            controllerRepository.registerControllerFactory(modelType.isInstance, factory)