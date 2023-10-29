package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.entities.powerups.view.PowerUpViewManager

class PowerUpController(var powerUpView: PowerUpViewManager, powerUp: PickablePowerUp):
    powerUp.overlapping += { (colliders) =>
        powerUpView = powerUpView.removeLastAddedView()
    }

    private def spawn(powerUp: PickablePowerUp): Unit = ???
        //need factory
        //this.powerUpView.addView(null)
        //view.show()

