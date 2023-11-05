package org.aas.sbtanks.entities.powerups




trait TimeablePowerUp(var duration: Double):
    def decreaseDuration(delta: Double): Unit =
      duration -= delta


    def isExpired: Boolean =
      duration <= 0
