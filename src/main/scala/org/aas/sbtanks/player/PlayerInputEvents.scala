package org.aas.sbtanks.player

import scala.collection.mutable
import org.aas.sbtanks.event.EventSource

trait PlayerInputEvents:
    val moved = EventSource[(Double, Double)]