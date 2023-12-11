package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.PowerUpChainBinder
import org.aas.sbtanks.entities.powerups.view.PowerUpView
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.player.PlayerTank
import scalafx.scene.Node
import scalafx.stage.Stage

/**
 * A controller for managing the behavior of pickable power-ups in the game.
 *
 * @tparam E   The type of the entity associated with the power-up.
 * @param context   The entity repository context providing access to the game stage.
 * @param entityRepo The repository container for managing entities MVCs.
 * @param powerUp    The pickable power-up instance associated with this controller.
 * @param view       The view representing the power-up in the game.
 * @param viewScale  The scale factor for the power-up view visualization.
 * @param pickup     An event source for notifying observers when a power-up is picked up. Provide a powerup if its picked up or an empty optional if it disappear before pick-up
 */
class PowerUpController[VSK, VS, E](using context: EntityRepositoryContext[Stage, VSK, VS])(entityRepo: EntityMvRepositoryContainer[AnyRef, Node], powerUp: PickablePowerUp[E], view: PowerUpView, viewScale: Double, pickup: EventSource[Option[PowerUp[E]]]) extends Steppable:


    private var timeToDisappear: Double = 5.0

    view.move(powerUp.positionX * viewScale, powerUp.positionY * viewScale)
    view.show(timeToDisappear)

    /**
     * Handles the overlapping collision event of the power-up with other entities.
     * It's used to remove its own mv wether a powerup is picked-up by the player tank
     *
     * @param colliders The colliders with which the power-up is overlapping.
     */
    powerUp.overlapping += { (colliders) =>
        
        checkTankCollision(colliders).headOption match
            case Some(_) =>
                pickup(Option(powerUp))
                entityRepo.removeModelView(powerUp)
            case _ =>

    }

    /**
     * Checks for tank collision among a sequence of colliders.
     *
     * @param colliders The colliders to check for tank collision.
     * @return A sequence of tanks that have collided with the power-up or an empty sequence if nothing is found.
     */
    private def checkTankCollision(colliders: Seq[Collider]): Seq[Tank] =
        colliders.collect:
            case element: PlayerTank => element


    /**
     * Updates the state of the power-up controller based on the elapsed time.
     * The elapsed time is detracted from the `timeToDisappaer` total time
     *
     * @param delta The elapsed time since the last update.
     * @return The updated power-up controller.
     */
    override def step(delta: Double): this.type =
        timeToDisappear -= delta
        if timeToDisappear <= 0.0 then
            pickup(Option.empty)
            entityRepo.removeModelView(powerUp)
        this

object PowerUpController:
    def factory[E](viewScale: Double, entityRepo: EntityMvRepositoryContainer[AnyRef, Node], pickup: EventSource[Option[PowerUp[E]]])(context: EntityRepositoryContext[Stage, ?, ?], powerUp: PickablePowerUp[E], view: PowerUpView) =
        new PowerUpController(using context)(entityRepo, powerUp, view, viewScale, pickup)




