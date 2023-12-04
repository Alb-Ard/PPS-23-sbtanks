package org.aas.sbtanks.common

trait Steppable:
    def step(delta: Double): this.type