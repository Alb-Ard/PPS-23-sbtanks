package org.aas.sbtanks.player

import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.SteppedBehaviour
import org.aas.sbtanks.entities.tank.view.TankView

type ControllableTank = SteppedMovementDirectionBehaviour & MovementBehaviour

abstract class PlayerTankController[A <: PlayerInputEvents](tank: ControllableTank, view: TankView, protected val inputEvents: A) extends SteppedBehaviour:
    tank.stepMoved += { (x, y) => 
        tank.moveRelative(x, y)
        val rotation = Math.toDegrees(x match
            case 0 =>  Math.acos(y % 1)
            case _ => Math.asin(x % 1))
        view.look(rotation)
    }
    tank.moved += view.move
    inputEvents.moved += tank.moveTowards

    override def step(delta: Double): Unit = 
        tank.step(delta)
