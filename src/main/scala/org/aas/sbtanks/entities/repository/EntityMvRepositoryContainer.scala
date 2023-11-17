package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.event.EventSource
import scala.reflect.ClassTag

abstract class EntityMvRepositoryContainer[Model, View]:
    case class ViewReplacedArgs(model: Model, oldView: Option[View], newView: Option[View])

    val modelViewAdded = EventSource[(Model, Option[View])]()
    val modelViewReplaced = EventSource[ViewReplacedArgs]()
    val modelViewRemoved = EventSource[(Model, Option[View])]()

    private var modelRepository = Seq.empty[Model]
    private var viewRepository = Seq.empty[View]

    private var modelViewReferences = Map.empty[Model, View]

    def addModelView(model: Model, view: Option[View]): this.type =
        modelRepository = modelRepository :+ model
        viewRepository = view match
            case None => 
                viewRepository
            case Some(v) =>
                viewRepository = viewRepository :+ v
                modelViewReferences = modelViewReferences + ((model, v))
                viewRepository
        modelViewAdded(model, view)
        this

    def replaceView(model: Model, newView: Option[View]): this.type =
        val (newViewRepository: Seq[View], oldView: Option[View]) = modelViewReferences.get(model).map(v => {
            viewRepository.filterNot(v.equals)
            modelViewReferences = modelViewReferences - model
            (viewRepository, Option(v))
        }).getOrElse((viewRepository, Option.empty[View]))
        viewRepository = newView match
            case None => 
                newViewRepository
            case Some(v) =>
                modelViewReferences = modelViewReferences + ((model, v))
                newViewRepository :+ v
        modelViewReplaced(ViewReplacedArgs(model, oldView, newView))
        this

    def entityModelCount = modelRepository.size

    def entityViewCount = viewRepository.size

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
        viewRepository = view match
            case None =>
                viewRepository
            case Some(v) => 
                modelViewReferences = modelViewReferences - model
                viewRepository.filterNot(v.equals)
        modelViewRemoved(model, view)
        this
    
object EntityMvRepositoryContainer:
    extension [M, V](repository: EntityMvRepositoryContainer[M, V])
        def entitiesOfViewType[V1 <: V](using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V1]) = 
            repository.entitiesOfMvTypes[M, V1]
        
        def entities(using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V]) = repository.entitiesOfModelType[M]