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
import org.aas.sbtanks.Main.jfxImageView2sfx
import org.aas.sbtanks.resources.scalafx.JFXImageLoader






/**
 * A controller for managing power-up binding behaviors in the game.
 *
 *
 * @param entityRepo The repository container for managing entities MVCs.
 * @param tankPowerUpsBinder The power-up binder specifically only for tank-related power-ups.
 * @param pickup            An event source to be notified on when a tank-related power-up is picked up.
 */
class PowerUpBinderController(entityRepo: EntityMvRepositoryContainer[AnyRef, Node], tankPowerUpsBinder: PowerUpChainBinder[Tank], pickup: EventSource[PowerUp[Tank]]) extends Steppable:

    /*
        TODO: need to get:
            - level clear (unbind player entity) X
            - entity destruction (unbind enemies entities) X
            - charged entity destruction (invoke new PowerUp) X
            - powerup pickup (chain new effect) V
     */

    /**
     * Handles the pickup event of tank-related power-ups.
     * Chains the picked-up power-up to the tank power-up binder.
     */
    pickup += { powerUp =>
        tankPowerUpsBinder.chain(powerUp)
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
                  tankPowerUpsBinder.unchain



    /**
     * Sets a new pickable power-up in the game world.
     * TODO: This method is a placeholder and requires a factory for power-up type and position generation.
     */
    private def setNewPickablePowerUp() =
        // placeholder, need random factory
        val p: PickablePowerUp[Tank] = new GrenadePowerUp with PositionBehaviour(8.0, 2.0) with CollisionBehaviour(1, 1, CollisionLayer.TanksLayer, Seq(CollisionLayer.TanksLayer))

        entityRepo.addModelView(
            p,
            Option(new JFXPowerUpView(JFXImageLoader.loadFromResources("entities/powerups/powerup_star.png", 16D, 4D)))
        )


    /**
     * Registers entities by binding power-ups to tanks and sets up destruction event handling.
     *
     * @param tanks The sequence of tanks to bind power-ups to.
     * @return The updated controller instance.
     */
    def registerEntities(tanks: Seq[Tank]): this.type =

        tanks.map(t => {
                tankPowerUpsBinder.bind(t)
                t
            })
            .collect:
                case tank: Tank with DamageableBehaviour =>
                    tank.destroyed += {_ =>
                        tankPowerUpsBinder.unbind(tank)
                        if (tank.isCharged) this.setNewPickablePowerUp()
                    }
        this







