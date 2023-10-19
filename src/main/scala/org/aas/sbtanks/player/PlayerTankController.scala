package org.aas.sbtanks.player

import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.SteppedBehaviour
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.view.TankView.lookInDirection

type ControllableTank = SteppedMovementDirectionBehaviour & MovementBehaviour & PositionBehaviour

abstract class PlayerTankController[A <: PlayerInputEvents](tank: ControllableTank, view: TankView, viewScale: Double, protected val inputEvents: A) extends SteppedBehaviour:
    tank.stepMoved += { (x, y) =>
        tank.moveRelative(x, y)
        view.isMoving(x != 0 || y != 0)
    }
    tank.directionChanged += view.lookInDirection
    tank.moved += { (x, y) => view.move(x * viewScale, y * viewScale) }
    inputEvents.moved += tank.moveTowards

    override def step(delta: Double): Unit = 
        tank.step(delta)
