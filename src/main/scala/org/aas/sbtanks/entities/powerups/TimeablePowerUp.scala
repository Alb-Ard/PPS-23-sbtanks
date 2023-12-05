package org.aas.sbtanks.entities.powerups




trait TimeablePowerUp(var duration: Double):
    private val originalDuration: Double = duration

    def decreaseDuration(delta: Double): this.type =
        duration -= delta
        this

    def resetDuration(): this.type =
        duration = originalDuration
        this


    def isExpired: Boolean =
        duration <= 0
