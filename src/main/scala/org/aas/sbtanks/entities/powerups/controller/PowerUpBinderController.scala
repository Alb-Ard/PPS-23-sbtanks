package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.powerups.{PowerUpChainBinder, TimeablePowerUp}
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.structure.Tank
import scalafx.stage.Stage



class PowerUpBinderController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS])(tankPowerUpsBinder: PowerUpChainBinder[Tank]) extends Steppable:

    /*
        TODO: need to get:
            - level clear (unbind player entity) X
            - entity destruction (unbind enemies entities) X
            - charged entity destruction (invoke new PowerUp) X
            - powerup pickup (chain new effect) V
     */


    override def step(delta: Double): this.type =
        this.decreaseTimeablesTime(delta)
        this




    private def decreaseTimeablesTime(deltaTime: Double) =
        tankPowerUpsBinder.getPowerUps.collect:
            case element: TimeablePowerUp =>
                element.decreaseDuration(deltaTime)
                if element.isExpired then
                    tankPowerUpsBinder.unchain(element)
