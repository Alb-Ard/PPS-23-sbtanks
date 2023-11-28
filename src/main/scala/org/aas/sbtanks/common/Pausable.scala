package org.aas.sbtanks.common

trait Pausable:
    private var paused = false

    def isPaused = paused

    def setPaused(paused: Boolean): this.type =
        this.paused = paused
        this

object Pausable:
    extension (pausable: Pausable)
        def togglePaused() =
            pausable.setPaused(!pausable.isPaused)