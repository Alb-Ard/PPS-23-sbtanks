package org.aas.sbtanks.enemies.ai.fsm.movement

import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.State.State
import DirectionMove.{CanMoveTo, CannotMoveTo}
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}

import scala.util.Random

object AiMovementStateMachine extends AbstractStateMachine[MovementEntity, DirectionMove]:

    override def transition(value: DirectionMove): State[MovementEntity, Unit] =
        for
            dir <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            newDir <- (dir, value) match
                case ((_, y), CanMoveTo) if y > 0.0 => pure(Bottom)
                case ((_, y), CannotMoveTo) if y > 0.0 => Random.nextInt(2) match {
                    case 0 => pure(Right)
                    case 1 => pure(Left)
                }

                case ((x, _), CanMoveTo) if x > 0.0 => pure(Bottom)
                case ((x, _), CannotMoveTo) if x > 0.0 => Random.nextInt(2) match {
                    case 0 => pure(Left)
                    case _ => pure(Top)
                }

                case ((x, _), CanMoveTo) if x < 0.0 => pure(Bottom)
                case ((x, _), CannotMoveTo) if x < 0.0 => Random.nextInt(2) match {
                    case 0 => pure(Right)
                    case _ => pure(Top)
                }

                case ((_, y), CanMoveTo) if y < 0.0 => Random.nextInt(2) match {
                    case 0 => pure(Right)
                    case _ => pure(Left)
                }
                case ((_, y), CannotMoveTo) if y < 0.0 => pure(Bottom)

                case _ => pure(Bottom)

            _ <- modify(e => e.setDirection(newDir._1, newDir._2).asInstanceOf[MovementEntity])
        yield
            ()



    def checkMove(): State[MovementEntity, Option[(Double, Double)]] =
        for
            s0 <- getState
            x <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            if s0.testMoveRelative(x._1, x._2)
        yield x

    private def moveNext(remainingIterations: Int): State[MovementEntity, (Double, Double)] =
        for
            c <- checkMove()
            d <- c match
                case Some(d) => pure(d)
                case None if remainingIterations > 0 =>
                    transition(CannotMoveTo).flatMap(_ => moveNext(remainingIterations-1))
                case _ => pure(NoDirection)
        yield d

    private def getNewCoord: State[MovementEntity, (Double, Double)] =
        for
            d <- moveNext(5)
            c <- gets(x => (x.positionX, x.positionY))
            newPos <- d match
                case Bottom => pure((c._1, c._2 + 1))
                case Right => pure((c._1 + 1, c._2))
                case Left => pure((c._1 - 1, c._2))
                case Top => pure((c._1, c._2 - 1))
                case _ => pure((c._1, c._2))
        yield newPos


    def computeState(): State[MovementEntity,(Double, Double)] =
        for
            _ <- transition(CanMoveTo)
            newCoord <- getNewCoord
        yield newCoord


object AiMovementStateMachineUtils:
    def computeAiState(entity: MovementEntity): ((Double, Double), MovementEntity) =
        AiMovementStateMachine.computeState().runAndTranslate(entity)

