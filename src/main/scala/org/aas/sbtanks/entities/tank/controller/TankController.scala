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
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

abstract class TankController[+A <: TankInputEvents](tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double, protected val inputEvents: A) extends Steppable:
    tank.directionChanged += { (x, y) => 
        view.lookInDirection(x, y)
        view.isMoving(tank.directionX != 0 || tank.directionY != 0)
    }
    tank.positionChanged += { (x, y) => view.move(x * viewScale, y * viewScale) }
    inputEvents.moveDirectionChanged += tank.setDirection 
    inputEvents.shootPerfomed += { _ => shoot() }

    override def step(delta: Double) = 
        tank.moveRelative(tank.directionX * tank.tankData.speed * speedMultiplier, tank.directionY * tank.tankData.speed * speedMultiplier)
        this
    
    protected def shoot(): this.type

object TankController:    
    type ControllableTank = Tank
        with DirectionBehaviour
        with MovementBehaviour
        with PositionBehaviour
        with CollisionBehaviour
