package org.aas.sbtanks.entities.tank.controller

import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.common.view.DirectionableView.lookInDirection
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.structure.Tank
import TankController.ControllableTank
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.behaviours.DamageableBehaviour

trait TankController(tanks: Seq[(ControllableTank, TankView)], viewScale: Double, tileSize: Double):
    tanks.foreach((t, v) => t.directionChanged += { (x, y) => 
        v.lookInDirection(x, y)
        v.isMoving(t.directionX != 0 || t.directionY != 0)
    })
    tanks.foreach((t, v) => t.positionChanged += { (x, y) => v.move(x * viewScale * tileSize, y * viewScale * tileSize) })

object TankController:    
    type ControllableTank = Tank
        with DirectionBehaviour
        with MovementBehaviour
        with PositionBehaviour
        with CollisionBehaviour
        with TankMultipleShootingBehaviour
        with DamageableBehaviour
