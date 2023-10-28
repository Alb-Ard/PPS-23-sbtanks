package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.{TankData, structure}
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.powerups.PowerUpChain.*
import org.scalatest.BeforeAndAfterEach

class TankPowerUpSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:


    val defaultHealth: Int = BasicTankData.supplyData.health
    val defaultSpeed: Int = BasicTankData.supplyData.speed
    val defaultBulletSpeed: Int = BasicTankData.supplyData.bulletSpeed

    var tank: Tank = BasicTank()

    override def beforeEach(): Unit = {
        tank = BasicTank()
    }

    "It" should "be possible to apply a power-up effect to a tank" in {
        HealthUp(tank).tankData.health should be (defaultHealth + 10)
        SpeedUp(tank).tankData.speed should be (defaultSpeed + 10)
        SpeedBulletUp(tank).tankData.speed should be (defaultBulletSpeed + 10)
    }

    "Whenever a power-up is applied it" should "be possible to revert its effect" in {
        (HealthUp revert HealthUp(tank)).tankData.health should be (defaultHealth)
        (SpeedUp revert SpeedUp(tank)).tankData.speed should be (defaultSpeed)

        (HealthUp + SpeedUp).unchain(HealthUp).apply(tank).tankData should be (TankData(defaultHealth, defaultSpeed + 10, defaultBulletSpeed))
    }

    "It" should "be possible to combine the effect of multiple power-ups (operator api)" in {

        ((HealthUp + SpeedUp + SpeedBulletUp) apply tank).tankData should be (TankData(defaultHealth + 10, defaultSpeed + 10, defaultBulletSpeed + 10))

    }

    "It" should "be possible to combine the effect of multiple power-ups (fluent api)" in {

        PowerUpChain(Seq())
            .chain(HealthUp)
            .chain(SpeedUp)
            .chain(SpeedBulletUp).apply(tank).tankData should be(TankData(defaultHealth + 10, defaultSpeed + 10, defaultBulletSpeed + 10))
    }


