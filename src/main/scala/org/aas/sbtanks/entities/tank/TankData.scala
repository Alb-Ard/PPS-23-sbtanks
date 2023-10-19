package org.aas.sbtanks.entities.tank


case class TankData(health: Int, speed: Int)

trait TankDataUpdater:
    self: TankData =>

    def updateHealth(f: Int => Int): TankData & TankDataUpdater = new TankData(f(health), speed) with TankDataUpdater
    def updateSpeed(f: Int => Int): TankData & TankDataUpdater = new TankData(health, f(speed)) with TankDataUpdater

object TankExample extends App:

    var initialTankData: TankData = TankData(100, 50)


    val modifiableTank = new TankData(initialTankData.health, initialTankData.speed) with TankDataUpdater

    // Update tank data
    val updatedTank = modifiableTank
      .updateHealth(_ + 20)
      .updateSpeed(_ * 2)


    println(s"Initial Tank Data: $initialTankData")
    println(s"Updated Tank Data: $updatedTank")

