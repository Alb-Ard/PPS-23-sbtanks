package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.effects.Grenade.GrenadePowerUp
import org.aas.sbtanks.entities.powerups.effects.Helmet.HelmetPowerUp
import org.aas.sbtanks.entities.powerups.effects.Star.StarPowerUp
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp
import org.aas.sbtanks.entities.tank.factories.BasicTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer

import scala.reflect.ClassTag
import scala.util.Random



enum PowerUpType:
    case Grenade
    case Helmet
    case Star
    case Timer

object PickablePowerUpFactory:
    private lazy val grenadeInstance: PickablePowerUp[Tank] =
        new GrenadePowerUp() with PositionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val helmetInstance: PickablePowerUp[Tank] =
        new HelmetPowerUp() with PositionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val starInstance: PickablePowerUp[Tank] =
        new StarPowerUp() with PositionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val timerPowerUp: PickablePowerUp[Tank] =
        new TimerPowerUp() with PositionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))


    def providePowerUp(powerUpType: PowerUpType) =
        powerUpType match
            case PowerUpType.Grenade => grenadeInstance
            case PowerUpType.Helmet => helmetInstance
            case PowerUpType.Star => starInstance
            case PowerUpType.Timer => timerPowerUp


    def getRandomPowerUp =
        val powerUpTypes = PowerUpType.values
        val randomIndex = Random.nextInt(powerUpTypes.length)
        providePowerUp(powerUpTypes(0))






