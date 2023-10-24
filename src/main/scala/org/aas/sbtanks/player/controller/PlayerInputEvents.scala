package org.aas.sbtanks.player.controller

import org.aas.sbtanks.event.EventSource

trait PlayerInputEvents:
    val moveDirectionChanged = EventSource[(Double, Double)]
    val shootPerfomed = EventSource[Unit]