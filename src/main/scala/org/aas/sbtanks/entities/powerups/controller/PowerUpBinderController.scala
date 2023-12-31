package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, PositionBehaviour}
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.effects.Grenade.GrenadePowerUp
import org.aas.sbtanks.entities.powerups.view.JFXPowerUpView
import org.aas.sbtanks.entities.powerups.{PowerUpChainBinder, TimeablePowerUp}
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.physics.CollisionLayer
import scalafx.scene.Node
import scalafx.scene.image.Image
import javafx.scene.image as jfxsi
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.view.ObstacleView
import org.aas.sbtanks.entities.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.entities.powerups.effects.Helmet.HelmetPowerUp
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import org.aas.sbtanks.entities.powerups.effects.Star.StarPowerUp
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp
import org.aas.sbtanks.physics.PhysicsContainer



/**
 * A controller for managing power-up binding behaviors in the game.
 *
 *
 * @param entityRepo The repository container for managing entities MVCs.
 * @param tankPowerUpsBinder The power-up binder specifically only for tank-related power-ups.
 * @param pickup An event source to be notified on when a tank-related power-up is picked up or it disappaer
 * @param tankSpawn An event source to be notified on when a tank spawn in the level
 */
class PowerUpBinderController(using PhysicsContainer)(entityRepo: EntityMvRepositoryContainer[AnyRef, Node], width: Double, height: Double, tankPowerUpsBinder: PowerUpChainBinder[Tank], pickup: EventSource[Option[PowerUp[Tank]]], tankSpawn: EventSource[Tank], tileSize: Double, viewScale: Double) extends Steppable:
    private val powerupFactory = new Object() with PickablePowerUpFactory()

    /**
     * Handles the pickup event of tank-related power-ups.
     * Chains the picked-up power-up to the tank power-up binder if its not empty, else it means
     */
    pickup += {
        case Some(p) =>
            tankPowerUpsBinder.chain(p)
        case None =>
    }



    tankSpawn += { tank =>
        registerEntity(tank)
    }

    override def step(delta: Double): this.type =
        this.decreaseTimeablesTime(delta)
        this

    /**
     * Decreases the duration of timeable power-ups and unchains expired timeable power-ups.
     *
     * @param deltaTime The elapsed time since the last update to subtract to.
     */
    private def decreaseTimeablesTime(deltaTime: Double): Unit =
        tankPowerUpsBinder.getPowerUps
            .collect:
                case element: TimeablePowerUp => element
            .map(_.decreaseDuration(deltaTime))
            .filter(_.isExpired)
            .foreach:
                case expiredPowerUp: TimeablePowerUp =>
                    tankPowerUpsBinder.unchain(expiredPowerUp.resetDuration())

    /**
     * Sets a new pickable power-up in the game world.
     */
    private def setNewPickablePowerUp() =
        if entityRepo.entitiesOfModelType[PowerUp[?]].isEmpty then
            val (p, imagePath) = powerupFactory.getRandomPowerUp(width, height)
            entityRepo.addModelView(
                p,
                Option(new JFXPowerUpView(JFXImageLoader.loadFromResources(imagePath, tileSize, viewScale)))
            )

    /**
     * Sets up a player by adding to it an initial power-up (HelmetPowerUp).
     *
     *
     * @param player The player tank to be set up with power-ups.
     * @param bind optionally initialize the player binding              
     */
    private def setUpPlayer(player: PlayerTank, bind: Boolean) =
        if bind then
            tankPowerUpsBinder.bind(player)
        tankPowerUpsBinder.chain(HelmetPowerUp())


    /**
     * Register an entity by binding power-ups and sets up destruction event handling.
     *
     * @param tank the tank to bind power-ups to.
     * @return The updated controller instance.
     */
    private def registerEntity(tank: Tank): this.type =
        tankPowerUpsBinder.bind(tank)
        tank match
            case tank1: PlayerTank => setUpPlayer(tank1, false)
            case _ =>

        tank match
            case tank: Tank with DamageableBehaviour =>
                tank.destroyed += { _ =>
                    tankPowerUpsBinder.unbind(tank)
                    tank match
                        case t: PlayerTank => setUpPlayer(t, true)
                        case _ =>
                    if (tank.isCharged) this.setNewPickablePowerUp()
                }
        this









