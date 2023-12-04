package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.common.Pausable
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext

trait EntityRepositoryPausableAdapter[M, V, C <: EntityRepositoryContext[?, ?, ?]] extends Pausable:
    this: EntityMvRepositoryContainer[M, V] with EntityControllerRepository[M, V, C] =>

    override def setPaused(paused: Boolean) = 
        super.setPaused(paused)
        editControllers((_, c) => c match
            case p: Pausable => p.setPaused(paused)
            case np => np
        )
        this
        