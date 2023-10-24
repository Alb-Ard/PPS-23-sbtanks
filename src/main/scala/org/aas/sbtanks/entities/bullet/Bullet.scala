package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour

case class Bullet(shooterTankData: TankData, direction: SteppedMovementDirectionBehaviour) :

    val bulletSpeed: Int = shooterTankData.speed
    val bulletDirection: (Double, Double) = direction.direction
    val bulletInitialPosition: shooterTankData.position + bulletDirection