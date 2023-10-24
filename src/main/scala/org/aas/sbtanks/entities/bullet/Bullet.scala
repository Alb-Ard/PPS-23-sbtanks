package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.entities.tank.TankData
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour

case class Bullet(speed: Int, direction: (Double, Double), position: (Double, Double)) :

    val bulletSpeed: Int = speed
    val bulletDirection: (Double, Double) = direction
    var bulletPosition:(Double, Double) = (position._1 + bulletDirection._1, position._2 + bulletDirection._2)