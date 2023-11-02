package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait DamageableBehaviour:
    val destroyed = EventSource[Unit]()

    def damage(): Unit