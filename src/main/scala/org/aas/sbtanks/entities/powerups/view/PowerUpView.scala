package org.aas.sbtanks.entities.powerups.view

trait PowerUpView:
    def show(): Unit

    def isTaken: Boolean

