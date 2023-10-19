package org.aas.sbtanks.player

import org.aas.sbtanks.event.EventSource

trait PlayerInputEvents:
    val moveDirectionChanged = EventSource[(Double, Double)]