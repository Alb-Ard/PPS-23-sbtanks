package org.aas.sbtanks.entities.tank.view

trait TankView:
    def move(positionX: Double, positionY: Double): Unit