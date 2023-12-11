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
import org.aas.sbtanks.physics.PhysicsContainer
import org.aas.sbtanks.physics.PhysicsWorld


/**
 * Enumeration representing different types of power-ups.
 */
enum PowerUpType:
    case Grenade
    case Helmet
    case Star
    case Timer

/**
 * Trait for a factory that creates pickable power-up instances.
 *
 * @param physicsContainer The container providing physics-related functionality.
 */
trait PickablePowerUpFactory(using PhysicsContainer):

    private val POWERUP_COLLISION_SIZE = 1

    private lazy val grenadeInstance: PickablePowerUp[Tank] =
        new GrenadePowerUp() with PositionBehaviour with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val helmetInstance: PickablePowerUp[Tank] =
        new HelmetPowerUp() with PositionBehaviour  with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val starInstance: PickablePowerUp[Tank] =
        new StarPowerUp() with PositionBehaviour  with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    private lazy val timerPowerUp: PickablePowerUp[Tank] =
        new TimerPowerUp() with PositionBehaviour  with CollisionBehaviour(POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE, CollisionLayer.PowerUpLayer, Seq(CollisionLayer.TanksLayer))

    /**
     * Provides an instance of a specific power-up type at a given width and height.
     *
     * @param powerUpType The type of power-up to provide.
     * @param width       The width of the power-up.
     * @param height      The height of the power-up.
     * @return An instance of the specified power-up type.
     */
    private def providePowerUpInstance(powerUpType: PowerUpType, width: Double, height: Double) =
        val p = powerUpType match
            case PowerUpType.Grenade => grenadeInstance
            case PowerUpType.Helmet => helmetInstance
            case PowerUpType.Star => starInstance
            case PowerUpType.Timer => timerPowerUp

        val positionProvider = (w, h) => PositionProvider(w, h)

        positionProvider(width, height)(Seq(CollisionLayer.TanksLayer, CollisionLayer.WallsLayer, CollisionLayer.NonWalkableLayer), POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE).findFreePosition() match
            case Some(x, y) =>
                p.setPosition(x, y)
            case _ =>
                p.setPosition(0, 0)

    private def getImagePathBy(powerUpType: PowerUpType) =
        "entities/powerups/powerup_"
            .concat(
                powerUpType match
                    case PowerUpType.Grenade => "grenade.png"
                    case PowerUpType.Helmet => "helmet.png"
                    case PowerUpType.Star => "star.png"
                    case PowerUpType.Timer => "timer.png"
            )

    /**
     * Generates a random power-up and its associated image path.
     *
     * @param width  The width of the power-up.
     * @param height The height of the power-up.
     * @return A tuple containing the generated power-up instance and its image path.
     */
    def getRandomPowerUp(width: Double, height: Double): (PickablePowerUp[?], String) =

        val powerUpTypes = PowerUpType.values

        val powerUpType = powerUpTypes(Random.nextInt(powerUpTypes.length))

        (providePowerUpInstance(powerUpType, width, height).asInstanceOf[PickablePowerUp[?]], getImagePathBy(powerUpType))
