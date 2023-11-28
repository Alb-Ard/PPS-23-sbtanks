package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.{PowerUpChainBinder, TimeablePowerUp}
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.event.EventSource
import scalafx.stage.Stage


/**
 * A controller for managing power-up binding behaviors in the game.
 *
 *
 * @param context           The entity repository context providing access to the game stage.
 * @param tankPowerUpsBinder The power-up binder specifically only for tank-related power-ups.
 * @param pickup            An event source to be notified on when a tank-related power-up is picked up.
 */
class PowerUpBinderController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS])(tankPowerUpsBinder: PowerUpChainBinder[Tank], pickup: EventSource[PowerUp[Tank]]) extends Steppable:

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

    /*
        TODO: powerup dispatch
     */

    /**
     * Decreases the duration of timeable power-ups and unchains expired timeable power-ups.
     *
     * @param deltaTime The elapsed time since the last update to subtract to.
     */
    private def decreaseTimeablesTime(deltaTime: Double) =
        tankPowerUpsBinder.getPowerUps.collect:
            case element: TimeablePowerUp =>
                element.decreaseDuration(deltaTime)
                if element.isExpired then
                    tankPowerUpsBinder.unchain(element)

