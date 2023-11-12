package org.aas.sbtanks.enemies.ai.fsm

import org.aas.sbtanks.enemies.ai.{AiMovement, AiMovementState}
import org.aas.sbtanks.enemies.ai.State.Monad

trait StateMachine[S[_], E, A]:
    this: StateModifier[S, E] =>

    def transition(value: A): S[Unit]







  /*
  def transition(value: Boolean): AiMovementState[Unit] =
      for
          dir <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
          newDir <- (dir, value) match
              case ((_, y), true) if y > 0.0 => pure(Bottom_Y)
              case ((_, y), false) if y > 0.0 => pure(Right_X)

              case ((x, _), true) if x > 0.0 => pure(Bottom_Y)
              case ((x, _), false) if x > 0.0 => pure(Left_X)

              case ((x, _), true) if x < 0.0=> pure(Bottom_Y)
              case ((x, _), false) if x < 0.0 => pure(Top_Y)

              case ((_, y), true) if y < 0.0 => pure(Left_X)
              case ((_, y), false) if y < 0.0 => pure(Bottom_Y)

          _ <- modify(e => e.setDirection(newDir._1, newDir._2).asInstanceOf[AiEntity])
      yield
          ()
   */


