package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.effects.Grenade.GrenadePowerUp
import org.aas.sbtanks.entities.powerups.effects.Helmet.HelmetPowerUp
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp
import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.TimeLimits.failAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.Console.in
import scala.language.postfixOps

class PowerUpEffectsSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:

    private var tank: Tank = BasicTank()
    private val NO_HEALTH = 0

    override def beforeEach(): Unit =

        tank = BasicTank()

    "A grenade powerup" should "lower the health of all enemies to zero" in:
        GrenadePowerUp(tank).tankData.health should be (NO_HEALTH)

    "A grenade powerup" should "not be reverted to its precedent state after its application" in:
        GrenadePowerUp.revert(GrenadePowerUp(tank)).tankData.health should be (NO_HEALTH)


    //"A timer power up" should "lower the tank speed and bullet speed to zero for a limited amount of time" in:
        //write a test that applies a TimerPowerUp to a tank and checks that the tank speed and bullet speed are zero, and that after a certain amount of time they are back to normal
        /*
        failAfter(1000 millis):
            TimerPowerUp(tank).tankData.speed should be (0)
            TimerPowerUp(tank).tankData.bulletSpeed should be (0) */

    "A timer power up" should "lower the tank speed and bullet speed to zero and when reverted restore it to its precedent value" in:

        TimerPowerUp(tank).tankData should matchPattern:
            case TankData(_, 0, 0) =>

        TimerPowerUp.revert(TimerPowerUp(tank)).tankData should be (tank.tankData)


    "An Helmet powerup" should "provide a tank with an invincibility status and when reverted restore it to its precedent value" in:
        HelmetPowerUp(tank).tankData.health should be (Int.MaxValue)

        HelmetPowerUp.revert(HelmetPowerUp(tank)).tankData.health should be (tank.tankData.health)