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

            _ <- modify(_.setDirection(newDir(0), newDir(1)).asInstanceOf[MovementEntity])
        yield
            ()



    private def checkMove(scaleFactor: Double): State[MovementEntity, Option[(Double, Double)]] =
        for
            s0 <- getState
            x <- gets(e => (e.directionX.asInstanceOf[Double], e.directionY.asInstanceOf[Double]))
            if s0.testMoveRelative(x(0) / scaleFactor, x(1) / scaleFactor)
        yield x

    private def moveNext(remainingIterations: Int, scaleFactor: Double): State[MovementEntity, (Double, Double)] =
        for
            c <- checkMove(scaleFactor)
            d <- c match
                case Some(d) => pure(d)
                case None if remainingIterations > 0 =>
                    transition(Try).flatMap(_ => moveNext(remainingIterations-1, scaleFactor))
                case _ => pure(NoDirection)
        yield d

    
    def computeState(maxIteration: Int, scaleFactor: Double): State[MovementEntity,(Double, Double)] =
        for
            _ <- transition(Reset)
            d <- moveNext(maxIteration, scaleFactor)
        yield d


object AiMovementStateMachineUtils:
    val MAX_ITERATION_BEFORE_DEFAULT = 5
    val SCALE_FACTOR_MOVEMENT = 100D
    
    def computeAiState(entity: MovementEntity, maxIteration: Int = MAX_ITERATION_BEFORE_DEFAULT, movementBias: Double = SCALE_FACTOR_MOVEMENT): ((Double, Double), MovementEntity) =
        AiMovementStateMachine.computeState(maxIteration, movementBias).runAndTranslate(entity)

