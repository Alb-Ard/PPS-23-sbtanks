package org.aas.sbtanks.behaviours

trait SteppedBehaviour[A <: SteppedBehaviour[A]]:
    def step(delta: Double): A