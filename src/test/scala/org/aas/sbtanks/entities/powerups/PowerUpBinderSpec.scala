package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.PowerUpChain.*
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp
import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.{ArmorTank, BasicTank}
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.obstacles.LevelObstacle.SteelWall
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PowerUpBinderSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:

    var binder: PowerUpChainBinder[Tank] = PowerUpChainBinder[Tank]
    var tank: Tank = BasicTank()

    val defaultHealth: Int = BasicTankData.supplyData.health
    val defaultSpeed: Int = BasicTankData.supplyData.speed
    val defaultBulletSpeed: Int = BasicTankData.supplyData.bulletSpeed

    override def beforeEach(): Unit =
        binder = PowerUpChainBinder[Tank]
        tank = BasicTank()


    "A PowerUpBinder" should "bind a powerup to a tank" in:

        val healthSpeedPowerUp: PowerUpChain[Tank] = SpeedUp + HealthUp + SpeedBulletUp

        binder.bind(tank)

        binder.chain(healthSpeedPowerUp)

        tank.tankData should be (TankData(defaultHealth + 10, defaultSpeed + 10, defaultBulletSpeed + 10))



    "It" should "be possible to bind and unbind a tank" in:

        binder.bind(tank)

        binder.chain(HealthUp)

        tank.tankData should be (TankData(defaultHealth + 10, defaultSpeed, defaultBulletSpeed))

        binder.unbind(tank)

        binder.chain(SpeedUp)

        tank.tankData should be ((TankData(defaultHealth + 10, defaultSpeed, defaultBulletSpeed)))



    "The powerUps" should "be applied only to entities already binded when the power-up was added" in:
        binder.chain(HealthUp)

        binder.bind(tank)
        

        binder.unchain(HealthUp)
        binder.unchain(SpeedUp)

        tank.tankData should be (TankData(defaultHealth, defaultSpeed, defaultBulletSpeed))

    "Same timeable powerups" should "be consistent and ignore powerups if applied multiple times" in:
        binder.bind(tank)

        val TIMER_DURATION = 2000L

        val t = TimerPowerUp()

        binder.chain(t).getPowerUps.head.asInstanceOf[TimeablePowerUp].decreaseDuration(1000)


        binder.chain(t)

        binder.getPowerUps should have size(1)

        binder.getPowerUps.head.asInstanceOf[TimeablePowerUp].duration should be (1000)















