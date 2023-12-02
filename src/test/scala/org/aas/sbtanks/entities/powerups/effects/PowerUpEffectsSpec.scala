package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.entities.powerups.effects.Grenade.GrenadePowerUp
import org.aas.sbtanks.entities.powerups.effects.Helmet.HelmetPowerUp
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp
import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.player.PlayerTank
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

    "A grenade powerup" should "destroyed an enemy" in:
        val g = new GrenadePowerUp

        val damageableEnemyTank = new BasicTank() with DamageableBehaviour:
            override protected def applyDamage(amount: Int): this.type =
                updateTankData(tankData.updateHealth(_ - 1))
                tankData.health match
                    case v if v <= 0 =>
                        destroyed(())
                        this
                    case _ => this

        damageableEnemyTank.destroyed += { _ =>
            succeed
        }

        g(damageableEnemyTank)

    "A grenade powerup" should "not be applied to players" in:
        val g = new GrenadePowerUp

        val damageableEnemyTank = new BasicTank() with DamageableBehaviour:
            override protected def applyDamage(amount: Int): this.type =
                updateTankData(tankData.updateHealth(_ - 1))
                tankData.health match
                    case v if v <= 0 =>
                        destroyed(())
                        this
                    case _ => this


        val damageablePlayerTank = new PlayerTank() with DamageableBehaviour:
            override protected def applyDamage(amount: Int): this.type =
                updateTankData(tankData.updateHealth(_ - 1))
                tankData.health match
                    case v if v <= 0 =>
                        destroyed(())
                        this
                    case _ => this

        damageablePlayerTank.destroyed += { _ =>
            fail()
        }


        damageableEnemyTank.destroyed += { _ =>
            succeed
        }


        g(damageablePlayerTank)
        g(damageableEnemyTank)



    "A timeable powerup" should "be applied only for a certain amount of time" in:
        import org.aas.sbtanks.entities.powerups.effects.TimerPowerUpUtils.STOP_TIME_POWER_UP_DURATION

        val t = new TimerPowerUp

        t.duration should be(STOP_TIME_POWER_UP_DURATION)

        t.decreaseDuration(1000).duration should be(STOP_TIME_POWER_UP_DURATION - 1000)

    "A timer power up" should "lower the tank speed and bullet speed to zero and when reverted restore it to its precedent value" in:

        val t = new TimerPowerUp

        t(tank).tankData should matchPattern:
            case TankData(_, 0, 0) =>

        t.revert(t(tank)).tankData should be (tank.tankData)


    "An Helmet powerup" should "provide a tank with an invincibility status where its not possible for it to be damaged" in:
        var h = new HelmetPowerUp

        val damageableTank = new BasicTank() with DamageableBehaviour:
            override protected def applyDamage(amount: Int): this.type =
                updateTankData(tankData.updateHealth(_ - 1))
                tankData.health match
                    case v if v <= 0 =>
                        destroyed(())
                        this
                    case _ => this


        val undamageableTank = h(damageableTank)
            .asInstanceOf[DamageableBehaviour]
            .damage(1)
            .asInstanceOf[Tank with DamageableBehaviour]

        undamageableTank.tankData.health should be (BasicTankData.supplyData.health)

        h.revert(undamageableTank)
            .asInstanceOf[DamageableBehaviour]
            .damage(1)
            .asInstanceOf[Tank].tankData.health should be(BasicTankData.supplyData.health - 1)







        /*
        val p = new HelmetPowerUp with PositionBehaviour


        tank = p(tank)

        tank = tank.damage(1)
        println(tank.tankData.health)


        tank = p.revert(tank)

        tank = tank.damage(1)
        println(tank.tankData.health)
         */



        //HelmetPowerUp.revert(HelmetPowerUp(tank)).tankData.health should be (tank.tankData.health)