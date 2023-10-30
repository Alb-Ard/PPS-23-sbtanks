package org.aas.sbtanks.entities.tank.controller

import org.aas.sbtanks.event.EventSource

trait TankInputEvents:
    val moveDirectionChanged = EventSource[(Double, Double)]
    val shootPerfomed = EventSource[Unit]