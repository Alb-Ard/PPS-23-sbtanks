package org.aas.sbtanks.enemies.ai.fsm.movement

import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.State.State
import DirectionMovePolicy.{Try, Reset}
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}

import scala.util.Random

object AiMovementStateMachine extends AbstractStateMachine[MovementEntity, DirectionMovePolicy]:
    import AiMovementStateMachineUtils.*

    override def transition(value: DirectionMovePolicy): State[MovementEntity, Unit] =
        for
            dir <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            newDir <- (dir, value) match
                case (Bottom, Reset) => pure(Bottom)
                case (Bottom, Try) => Random.nextInt(2) match 
                    case 0 => pure(Right)
                    case 1 => pure(Left)
                

                case (Right, Reset) => pure(Bottom)
                case (Right, Try) => Random.nextInt(2) match 
                    case 0 => pure(Left)
                    case _ => pure(Top)
                

                case (Left, Reset) => pure(Bottom)
                case (Left, Try) => Random.nextInt(2) match 
                    case 0 => pure(Right)
                    case _ => pure(Top)
                

                case (Top, Reset) => Random.nextInt(2) match 
                    case 0 => pure(Right)
                    case _ => pure(Left)
                
                case (Top, Try) => pure(Bottom)

                case _ => pure(Bottom)

            _ <- modify(e => e.setDirection(newDir._1, newDir._2).asInstanceOf[MovementEntity])
        yield
            ()



    def checkMove(): State[MovementEntity, Option[(Double, Double)]] =
        for
            s0 <- getState
            x <- gets(e => (e.directionX.asInstanceOf[Double], e.directionY.asInstanceOf[Double]))
            if s0.testMoveRelative(x._1 / 100D, x._2 / 100D)
        yield x

    private def moveNext(remainingIterations: Int): State[MovementEntity, (Double, Double)] =
        for
            c <- checkMove()
            d <- c match
                case Some(d) => pure(d)
                case None if remainingIterations > 0 =>
                    transition(Try).flatMap(_ => moveNext(remainingIterations-1))
                case _ => pure(NoDirection)
        yield d

    private def getNewCoord: State[MovementEntity, (Double, Double)] =
        for
            d <- moveNext(MAX_ITERATION_BEFORE_DEFAULT)
            c <- gets(x => (x.positionX, x.positionY))
            newPos <- pure((c._1 + d._1, c._2 + d._2))
        yield newPos


    def computeState(): State[MovementEntity,(Double, Double)] =
        for
            _ <- transition(Reset)
            newCoord <- getNewCoord
        yield newCoord


object AiMovementStateMachineUtils:
    val MAX_ITERATION_BEFORE_DEFAULT = 5
    
    def computeAiState(entity: MovementEntity): ((Double, Double), MovementEntity) =
        AiMovementStateMachine.computeState().runAndTranslate(entity)

