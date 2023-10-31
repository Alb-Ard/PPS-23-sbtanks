package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.entities.powerups.view.{JFXPowerUpView, PowerUpView, PowerUpViewManager}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.Collider

class PowerUpController(var powerUpView: PowerUpViewManager, powerUp: PickablePowerUp):
    powerUp.overlapping += { (colliders) =>
        powerUpView = powerUpView.removeLastAddedView()

        val enemyTanks: Tank = checkEnemyTankCollision(colliders).head

        /*
            TODO: add a method to add power-up effect (to implement after power-up effect implementation)
         */


    }

    private def checkEnemyTankCollision(colliders: Seq[Collider]): Seq[Tank] =
        colliders.collect:
            case element: Tank => element


    private def spawn(powerUp: PickablePowerUp): Unit =

        val view: PowerUpView = JFXPowerUpView("entities/powerups/powerup_star.png")
        view.show()

        this.powerUpView.addView(view)



