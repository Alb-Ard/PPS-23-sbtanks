package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.event.EventSource

abstract class EntityMvRepositoryContainer[Model, View]:
    val modelAdded = EventSource[Model]()
    val modelViewAdded = EventSource[(Model, View)]()

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

    def removeModelView(model: Model): this.type =
        modelRepository = modelRepository.filterNot(model.equals)
        viewRepository = modelReferences.getOrElse(model, null) match
            case null =>
                modelAdded(model)
                viewRepository
            case v => 
                viewRepository.filterNot(v.equals)
                modelViewRemoved(model, v.asInstanceOf[View])
                viewRepository
        modelReferences = modelReferences - model
        this