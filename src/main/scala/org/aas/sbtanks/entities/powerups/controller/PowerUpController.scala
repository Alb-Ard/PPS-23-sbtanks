package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.PowerUpChainBinder
import org.aas.sbtanks.entities.powerups.view.PowerUpView
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.player.PlayerTank
import scalafx.scene.Node
import scalafx.stage.Stage

class PowerUpController[VSK, VS, E](using context: EntityRepositoryContext[Stage, VSK, VS])(entityRepo: EntityMvRepositoryContainer[AnyRef, Node], powerUp: PickablePowerUp[E], view: PowerUpView, binder: PowerUpChainBinder[E]) extends Steppable:
    powerUp.overlapping += { (colliders) =>

        /*
            TODO: do not manipulate directly binder, but send an event to main powerup controller
         */
        checkTankCollision(colliders).foreach
            binder.chain(powerUp)

        entityRepo.removeModelView(powerUp)

    }

    private def checkTankCollision(colliders: Seq[Collider]): Seq[Tank] =
        colliders.collect:
            case element: Tank if element.isInstanceOf[PlayerTank] => element


    override def step(delta: Double): this.type = ???
    /*
        TODO: add this to main powerup controller time step
        powerUpBinder.getPowerUps.collect:
        case element: TimeablePowerUp =>
            element.decreaseDuration(deltaTime)
            if element.isExpired then
                powerUpBinder.unchain(element)
        this
     */



