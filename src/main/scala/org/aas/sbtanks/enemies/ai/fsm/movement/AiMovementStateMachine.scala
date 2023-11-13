package org.aas.sbtanks.enemies.ai.fsm.movement

import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.State.State
import DirectionMove.{CanMoveTo, CannotMoveTo}
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}

object AiMovementStateMachine extends AbstractStateMachine[MovementEntity, DirectionMove]:

    override def transition(value: DirectionMove): State[MovementEntity, Unit] =
        for
            dir <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            newDir <- (dir, value) match
                case ((_, y), CanMoveTo) if y > 0.0 => pure(Bottom_Y)
                case ((_, y), CannotMoveTo) if y > 0.0 => pure(Right_X)

                case ((x, _), CanMoveTo) if x > 0.0 => pure(Bottom_Y)
                case ((x, _), CannotMoveTo) if x > 0.0 => pure(Left_X)

                case ((x, _), CanMoveTo) if x < 0.0 => pure(Bottom_Y)
                case ((x, _), CannotMoveTo) if x < 0.0 => pure(Top_Y)

                case ((_, y), CanMoveTo) if y < 0.0 => pure(Left_X)
                case ((_, y), CannotMoveTo) if y < 0.0 => pure(Bottom_Y)

            _ <- modify(e => e.setDirection(newDir._1, newDir._2).asInstanceOf[MovementEntity])
        yield
            ()



    def checkMove(): State[MovementEntity, Option[(Double, Double)]] =
        for
            s0 <- getState
            x <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            if s0.testMoveRelative(x._1, x._2)
        yield x

    private def moveNext(): State[MovementEntity, (Double, Double)] =
        for
            c <- checkMove()
            d <- c match
                case Some(d) => pure(d)
                case None => transition(CannotMoveTo).flatMap(_ => moveNext())
        yield d

    private def getNewCoord: State[MovementEntity, (Double, Double)] =
        for
            d <- moveNext()
            c <- gets(x => (x.positionX, x.positionY))
            newPos <- d match
                case Bottom_Y => pure((c._1, c._2 + 1))
                case Right_X => pure((c._1 + 1, c._2))
                case Left_X => pure((c._1 - 1, c._2))
                case Top_Y => pure((c._1, c._2 - 1))
        yield newPos


    def computeState(): State[MovementEntity,(Double, Double)] =
        for
            newCoord <- getNewCoord
            _ <- transition(CanMoveTo)
        yield newCoord


object AiMovementStateMachineUtils:
    def computeAiState(entity: MovementEntity): ((Double, Double), MovementEntity) =
        AiMovementStateMachine.computeState().runAndTranslate(entity)


