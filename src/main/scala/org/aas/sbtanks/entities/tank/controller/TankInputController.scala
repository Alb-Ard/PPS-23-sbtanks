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
import org.aas.sbtanks.common.Pausable
import org.aas.sbtanks.physics.PhysicsContainer

abstract class TankInputController[+A <: TankInputEvents, S <: TankControllerMoveSound](using PhysicsContainer)(tank: ControllableTank, view: TankView, speedMultiplier: Double, viewScale: Double, tileSize: Double, protected val inputEvents: A, protected override val moveSound: S)
    extends TankController(tank, view, viewScale, tileSize, moveSound)
    with Steppable
    with Pausable:

    inputEvents.moveDirectionChanged += { (x, y) => isPaused match
        case false => tank.setDirection(x, y)
        case _ => ()
    } 
    inputEvents.shootPerfomed += { _ => isPaused match
        case false => shoot()
        case _ => ()
    }

    override def step(delta: Double) = 
        super.step(delta)
        tank.moveRelative(tank.directionX * tank.tankData.speed * speedMultiplier, tank.directionY * tank.tankData.speed * speedMultiplier)
        this
    
    override def setPaused(paused: Boolean) = 
        super.setPaused(paused)
