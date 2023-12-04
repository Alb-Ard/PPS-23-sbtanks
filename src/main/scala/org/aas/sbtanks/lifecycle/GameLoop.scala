package org.aas.sbtanks.lifecycle

import _root_.scalafx.animation.AnimationTimer
import org.aas.sbtanks.common.Pausable
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer

class GameLoop(repository: EntityMvRepositoryContainer[?, ?] with Steppable, pausables: Seq[Pausable]) extends Pausable:
    private var lastTimeNanos = System.nanoTime().doubleValue
    private val updateTimer = AnimationTimer(_ => {
        val currentTimeNanos = System.nanoTime().doubleValue
        val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
        repository.step(deltaTime).executeQueuedCommands()
        lastTimeNanos = currentTimeNanos
    })

    override def setPaused(paused: Boolean) = 
        super.setPaused(paused).isPaused match
            case false => updateTimer.start()
            case _ => updateTimer.stop()
        pausables.foreach(_.setPaused(paused))
        this