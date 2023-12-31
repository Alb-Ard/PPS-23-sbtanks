package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.PowerUpChain.*
import org.aas.sbtanks.entities.powerups.effects.Helmet.HelmetPowerUp
import org.aas.sbtanks.entities.powerups.effects.Star.StarPowerUp
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp
import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.{ArmorTank, BasicTank}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.tank.factories.PowerTankData
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour

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

        val TIMER_DURATION = 10L
        val DECREASE_TIME = 2L

        val t = TimerPowerUp()

        binder.chain(t).getPowerUps.head.asInstanceOf[TimeablePowerUp].decreaseDuration(DECREASE_TIME)


        binder.chain(t)

        binder.getPowerUps should have size(1)

        binder.getPowerUps.head.asInstanceOf[TimeablePowerUp].duration should be (TIMER_DURATION - DECREASE_TIME)

    "A powerup with no more enitities registered" should "be cleared on successive chaining call" in:
        binder.bind(tank)

        val ss = new StarPowerUp()

        val t = new TimerPowerUp()

        binder.chain(ss)


        binder.unbind(tank)

        binder.bind(tank)

        binder.chain(t)


        binder.getPowerUps should not contain(ss)

    "Special powerups, like StarPowerUp" should "be chained multiple times" in:
        val INCREASED_NUMBER_BULLETS = 2
        
        val playerTank = new PlayerTank() with TankMultipleShootingBehaviour with PositionBehaviour with DirectionBehaviour

        binder.bind(playerTank)

        val ss =  new StarPowerUp()

        binder.chain(ss)

        playerTank.tankData.bulletSpeed should be (PowerTankData.supplyData.bulletSpeed)


        binder.chain(ss)

        playerTank.shots should be (INCREASED_NUMBER_BULLETS)

        

















