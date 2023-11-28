package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.event.EventSource
import scala.reflect.ClassTag
import scala.collection.immutable.Queue

/**
  * Basic repository for model-view pairs
  */
abstract class EntityMvRepositoryContainer[Model, View]:
    case class ViewReplacedArgs(model: Model, oldView: Option[View], newView: Option[View])

    val modelViewAdded = EventSource[(Model, Option[View])]()
    val modelViewReplaced = EventSource[ViewReplacedArgs]()
    val modelViewRemoved = EventSource[(Model, Option[View])]()

    private var modelRepository = Seq.empty[Model]
    private var modelViewReferences = Map.empty[Model, View]
    private var commandQueue = Queue.empty[() => Any]

    /**
      * Exeucted the commands queued from the previous call
      *
      * @return This repository
      */
    def executeQueuedCommands(): this.type =
        while !commandQueue.isEmpty do
            val (command, newQueue) = commandQueue.dequeue
            commandQueue = newQueue
            command()
        this

    /**
      * Adds a model with an optional view to this repository
      *
      * @param model The model to add
      * @param view An option with a view to associate with this model or an empty optional
      * @return This repository
      */
    def addModelView(model: Model, view: Option[View]): this.type =
        modelRepository = modelRepository :+ model
        modelViewReferences = view match
            case None => modelViewReferences
            case Some(v) => modelViewReferences + ((model, v))
        modelViewAdded(model, view)
        this

    def replaceView(model: Model, newView: Option[View]): this.type =
        val (newModelViewReferences: Map[Model, View], oldView: Option[View]) = modelViewReferences.get(model)
            .map(v => (modelViewReferences - model, Option(v)))
            .getOrElse((modelViewReferences, Option.empty[View]))
        modelViewReferences = newView match
            case None => modelViewReferences
            case Some(v) => modelViewReferences + ((model, v))
        modelViewReplaced(ViewReplacedArgs(model, oldView, newView))
        this

    def entityModelCount = modelRepository.size

    def entityViewCount = modelViewReferences.size

    def entitiesOfModelType[M1 <: Model](using modelClassTag: ClassTag[M1], viewClassTag: ClassTag[View]) = 
        modelRepository.filter(modelClassTag.runtimeClass.isInstance)
                .map(m => (m.asInstanceOf[M1], modelViewReferences.get(m)))

    def entitiesOfMvTypes[M1 <: Model, V1 <: View](using modelClassTag: ClassTag[M1], viewClassTag: ClassTag[V1]) =
        modelRepository.filter(modelClassTag.runtimeClass.isInstance)
                .map(m => modelViewReferences.find((mm, _) => m == mm))
                .filter(mv => mv.isDefined)
                .map(mv => mv.get)
                .filter((_, v) => viewClassTag.runtimeClass.isInstance(v))
                .map((m, v) => (m.asInstanceOf[M1], v.asInstanceOf[V1]))

    def removeModelView(model: Model): this.type =
        modelRepository = modelRepository.filterNot(model.equals)
        val view = modelViewReferences.get(model)
        modelViewReferences = view match
            case None => modelViewReferences
            case Some(v) => modelViewReferences - model
        modelViewRemoved(model, view)
        this
    
    protected def queueCommand(command: () => Any): this.type =
        commandQueue = commandQueue :+ command
        this
    
object EntityMvRepositoryContainer:
    extension [M, V](repository: EntityMvRepositoryContainer[M, V])
        def entitiesOfViewType[V1 <: V](using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V1]) = 
            repository.entitiesOfMvTypes[M, V1]
        
        def entities(using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V]) = repository.entitiesOfModelType[M]
    
        def clear(using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V])(): repository.type = 
            repository.entities.foreach((m, _) => repository.removeModelView(m))
            repository