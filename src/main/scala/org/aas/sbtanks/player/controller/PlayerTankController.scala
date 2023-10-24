package org.aas.sbtanks.player.controller

import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.SteppedBehaviour
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.common.view.DirectionableView.lookInDirection
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.physics.CollisionLayer

type ControllableTank = SteppedMovementDirectionBehaviour 
    with MovementBehaviour
    with PositionBehaviour
    with CollisionBehaviour

abstract class PlayerTankController[A <: PlayerInputEvents](tank: ControllableTank, view: TankView, viewScale: Double, protected val inputEvents: A) extends SteppedBehaviour[PlayerTankController[A]]:
    tank.directionStepped += { (x, y) =>
        tank.moveRelative(x, y)
        view.isMoving(x != 0 || y != 0)
    }
    tank.directionChanged += view.lookInDirection
    tank.positionChanged += { (x, y) => view.move(x * viewScale, y * viewScale) }
    inputEvents.moveDirectionChanged += tank.moveTowards

    override def step(delta: Double) = 
        tank.step(delta)
        this
