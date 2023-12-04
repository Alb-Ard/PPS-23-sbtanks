package org.aas.sbtanks.common

import org.aas.sbtanks.event.EventSource

trait Pausable:
    val pauseChanged = EventSource[Boolean]

    private var paused = false

    def isPaused = paused

    def setPaused(paused: Boolean): this.type =
        this.paused = paused
        pauseChanged(paused)
        this

object Pausable:
    extension (pausable: Pausable)
        def togglePaused() =
            pausable.setPaused(!pausable.isPaused)