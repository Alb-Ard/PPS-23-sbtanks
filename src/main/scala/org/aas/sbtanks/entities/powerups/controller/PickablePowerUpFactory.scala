package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.spawn.PositionProvider
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

/*
    TODO: SETUP POSITIONS
 */
object PickablePowerUpFactory:

    val POWERUP_COLLISION_SIZE = 1

    private lazy val grenadeInstance: PickablePowerUp[Tank] =
        new GrenadePowerUp() with PositionBehaviour with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val helmetInstance: PickablePowerUp[Tank] =
        new HelmetPowerUp() with PositionBehaviour  with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val starInstance: PickablePowerUp[Tank] =
        new StarPowerUp() with PositionBehaviour  with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val timerPowerUp: PickablePowerUp[Tank] =
        new TimerPowerUp() with PositionBehaviour  with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))


    private def providePowerUp(powerUpType: PowerUpType) =
        powerUpType match
            case PowerUpType.Grenade => grenadeInstance
            case PowerUpType.Helmet => helmetInstance
            case PowerUpType.Star => starInstance
            case PowerUpType.Timer => timerPowerUp


    def getRandomPowerUp(width: Double, height: Double): PickablePowerUp[?] =
        val positionProvider = (w, h) => PositionProvider(w, h)

        val powerUpTypes = PowerUpType.values
        val randomIndex = Random.nextInt(powerUpTypes.length)
        val p = providePowerUp(powerUpTypes(3))

        val r = positionProvider(width, height)(Seq(CollisionLayer.TanksLayer, CollisionLayer.WallsLayer, CollisionLayer.NonWalkableLayer), POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE).findFreePosition() match
            case Some(x, y) =>
                p.setPosition(x, y)
            case _ =>
                p.setPosition(0, 0)

        r.asInstanceOf[PickablePowerUp[?]]









