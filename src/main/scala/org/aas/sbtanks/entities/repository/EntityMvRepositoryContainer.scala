package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.event.EventSource

abstract class EntityMvRepositoryContainer[Model, View]:
    case class ViewReplacedArgs(model: Model, oldView: Option[View], newView: Option[View])

    val modelAdded = EventSource[Model]()
    val modelViewAdded = EventSource[(Model, View)]()

    val modelViewReplaced = EventSource[ViewReplacedArgs]()

    val modelRemoved = EventSource[Model]()
    val modelViewRemoved = EventSource[(Model, View)]()

    private var modelRepository = Seq.empty[Model]
    private var viewRepository = Seq.empty[View]

    private var modelReferences = Map.empty[Model, View]

    def addModelView(model: Model, view: Option[View]): this.type =
        modelRepository = modelRepository :+ model
        viewRepository = view match
            case None => 
                modelAdded(model)
                viewRepository
            case Some(v) =>
                viewRepository = viewRepository :+ v
                modelReferences = modelReferences + ((model, v))
                modelViewAdded(model, v)
                viewRepository
        this

    def replaceView(model: Model, newView: Option[View]): this.type =
        val (newViewRepository: Seq[View], oldView: Option[View]) = modelReferences.get(model).map(v => {
            viewRepository.filterNot(v.equals)
            modelReferences = modelReferences - model
            (viewRepository, Option(v))
        }).getOrElse((viewRepository, Option.empty[View]))
        viewRepository = newView match
            case None => 
                newViewRepository
            case Some(v) =>
                modelReferences = modelReferences + ((model, v))
                newViewRepository :+ v
        modelViewReplaced(ViewReplacedArgs(model, oldView, newView))
        this

    def removeModelView(model: Model): this.type =
        modelRepository = modelRepository.filterNot(model.equals)
        viewRepository = modelReferences.getOrElse(model, null) match
            case null =>
                modelRemoved(model)
                viewRepository
            case v => 
                viewRepository.filterNot(v.equals)
                modelReferences = modelReferences - model
                modelViewRemoved(model, v.asInstanceOf[View])
                viewRepository
        this