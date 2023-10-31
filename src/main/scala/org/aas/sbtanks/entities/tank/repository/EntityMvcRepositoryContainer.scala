package org.aas.sbtanks.entities.tank.repository

abstract class EntityMvcRepositoryContainer():
    type Model = AnyRef
    type Controller = AnyRef
    type View

    type ControllerFactoryProvider[M <: Model, V <: View] = (M, V) => Controller

    private val modelRepository = EntitySeqRepository[Model]()
    private val viewRepository = EntitySeqRepository[View]()
    private val controllerRepository = EntitySeqRepository[Controller]()

    private var controllerFactories = Map.empty[Class[?], ControllerFactoryProvider[?, ?]]
    private var modelReferences = Map.empty[Model, View]

    def registerControllerFactory[M <: Model, V <: View](modelType: Class[M], factory: ControllerFactoryProvider[M, V]) =
        controllerFactories = controllerFactories + ((modelType, factory))
        this

    def addModelView[M <: Model, V <: View](model: M, view: V) =
        modelRepository.add(model)
        viewRepository.add(view)
        modelReferences = modelReferences + ((model, view))
        controllerFactories.getOrElse(model.getClass(), null) match
            case null => ()
            case f => f.asInstanceOf[ControllerFactoryProvider[M, V]] match
                case null => ()
                case tf => addController(tf(model, view))
        this

    def addController(controller: Controller) =
        controllerRepository.add(controller)
        this
    
    def removeModelView(model: Model) =
        modelRepository.remove(model)
        modelReferences.applyOrElse(model, _ => null) match
            case null => ()
            case v => viewRepository.remove(v.asInstanceOf[View])
        modelReferences = modelReferences - model
        this
    
    def removeController(controller: Controller) =
        controllerRepository.remove(controller)