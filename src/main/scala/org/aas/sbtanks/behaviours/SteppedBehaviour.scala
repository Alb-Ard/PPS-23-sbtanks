package org.aas.sbtanks.behaviours

trait SteppedBehaviour:
    def step(delta: Double): SteppedBehaviour