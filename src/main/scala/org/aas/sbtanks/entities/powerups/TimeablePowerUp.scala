package org.aas.sbtanks.entities.powerups




trait TimeablePowerUp(var duration: Double):
    def decreaseDuration(delta: Double): this.type =
      duration -= delta
      this


    def isExpired: Boolean =
      duration <= 0
