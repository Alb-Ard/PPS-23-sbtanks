package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.entities.tank.structure.Tank.FastTank
import org.aas.sbtanks.entities.tank.structure.Tank.ArmorTank
import org.aas.sbtanks.entities.tank.structure.Tank.PowerTank

/**
  * Object containing the point values to assign the player
  */
object PointsBonuses:
    /**
      * Calculates the points to give when destroying an enemy tank
      *
      * @param tank The destroyed tank
      * @return The points amount
      */
    def tankDestroyed(tank: Tank) = tank match
        case _ :BasicTank => 100
        case _ :FastTank => 200
        case _ :ArmorTank => 300
        case _ :PowerTank => 400
        case _ => 0
    
    /**
      * Calculates the points to give when collecting a powerup
      *
      * @return The points amount
      */
    def pickupCollected = 500