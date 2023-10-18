package org.aas.sbtanks.player

import org.aas.sbtanks.event.EventSource

trait PlayerInputEvents:
    val moved = EventSource[(Double, Double)]