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

abstract class TankInputController[+A <: TankInputEvents](tank: ControllableTank, view: TankView, speedMultiplier: Double, viewScale: Double, protected val inputEvents: A)
    extends TankController(Seq((tank, view)), viewScale)
    with Steppable:

    inputEvents.moveDirectionChanged += tank.setDirection 
    inputEvents.shootPerfomed += { _ => shoot() }

    override def step(delta: Double) = 
        tank.moveRelative(tank.directionX * tank.tankData.speed * speedMultiplier, tank.directionY * tank.tankData.speed * speedMultiplier)
        this
    
    protected def shoot(): this.type
