package org.aas.sbtanks.entities.tank.behaviours

trait SteppedBehaviour:
    def step(delta: Double): Unit