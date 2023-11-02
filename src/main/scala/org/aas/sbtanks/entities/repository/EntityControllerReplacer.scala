package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.common.Steppable

/**
  * Adds the ability to (un)register controller factories, to automatically create controllers from models
  *
  * @param context The context that will be passed to the controller factories
  */
trait EntityControllerReplacer[Model, View, Context <: EntityRepositoryContext[?]](using context: Context):
    this: EntityMvRepositoryContainer[Model, View] with EntityControllerRepository[Model, View, Context] =>

    private case class ControllerReplacer[ReplacerParams](validModelPredicate: Model => Boolean, replacer: ReplacerParams => Controller)
    private type ControllerWithViewReplacer = ControllerReplacer[(Controller, Context, Model, View)]
    private type ControllerWithoutViewReplacer = ControllerReplacer[(Controller, Context, Model)]

    private var controllerWithViewReplacers = Seq.empty[ControllerWithoutViewReplacer]
    private var controllerWithoutViewReplacers = Seq.empty[ControllerWithoutViewReplacer]

    modelViewReplaced += { p =>
        editControllers((model, controller) => {
            // TODO
            controller           
        })
    }

object EntityControllerReplacer:
    extension [Model, View, Context <: EntityRepositoryContext[?]](controllerRepository: EntityControllerRepository[Model, View, Context])
        def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: (Context, M, V) => EntityControllerRepository[Model, View, Context]#Controller) =
            controllerRepository.registerControllerFactory(modelType.isInstance, factory)

        def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: (Context, M) => EntityControllerRepository[Model, View, Context]#Controller) =
            controllerRepository.registerControllerFactory(modelType.isInstance, factory)