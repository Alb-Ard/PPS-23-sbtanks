package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.PowerUpChain.*
import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.{ArmorTank, BasicTank}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PowerUpBinderSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:

    var binder: PowerUpChainBinder[Tank] = PowerUpChainBinder[Tank]
    var tank: Tank = BasicTank()

    val defaultHealth: Int = BasicTankData.supplyData.health
    val defaultSpeed: Int = BasicTankData.supplyData.speed

    override def beforeEach(): Unit = {
        binder = PowerUpChainBinder[Tank]
        tank = BasicTank()
    }

    "A PowerUpBinder" should "bind a powerup to a tank" in {

        val healthSpeedPowerUp: PowerUpChain[Tank] = SpeedUp + HealthUp

        binder.bind(tank)

        binder.chain(healthSpeedPowerUp)

        tank.tankData should be (TankData(defaultHealth + 10, defaultSpeed + 10))

        /*
        binder.bind(tank2)

        binder.unchain(HealthUp).unchain(SpeedUp)

        tank1.tankData should be ((TankData(defaultHealth, defaultSpeed)))
        tank2.tankData should be ((TankData(defaultHealth, defaultSpeed)))
        */
    }

    "It" should "be possible to bind and unbind a tank" in {

        binder.bind(tank)

        binder.chain(HealthUp)

        tank.tankData should be (TankData(defaultHealth + 10, defaultSpeed))

        binder.unbind(tank)

        binder.chain(SpeedUp)

        tank.tankData should be ((TankData(defaultHealth + 10, defaultSpeed)))

    }

    "The powerUps" should "be applied only to entities already binded when the power-up is added" in {
        binder.chain(HealthUp)

        binder.bind(tank)

        binder.unchain(HealthUp)
        binder.unchain(SpeedUp)

        tank.tankData should be ((TankData(defaultHealth, defaultSpeed)))
    }


