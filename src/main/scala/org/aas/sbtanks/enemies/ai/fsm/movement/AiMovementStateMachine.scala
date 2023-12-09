package org.aas.sbtanks.enemies.ai.fsm.movement

import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.State.State
import DirectionMovePolicy.{Try, Reset}
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}

import scala.util.Random

/**
 *  object representing a state machine for controlling the movement behavior of AI entities.
 *  Extends AbstractStateMachine with ShootingEntity and FocusPolicy types.
 */
object AiMovementStateMachine extends AbstractStateMachine[MovementEntity, DirectionMovePolicy]:
    import AiMovementStateMachineUtils.*

    /**
     * Transitions the state based on the given transition.
     *
     * @param value The direction move policy.
     * @return A State datatype without result return representing the transition.
     */
    override def transition(value: DirectionMovePolicy): State[MovementEntity, Unit] =
        for
            dir <- gets(x => (x.directionX, x.directionY))
            newDir <- (dir, value) match

                case (Bottom, Reset) => pure(Bottom)
                case (Bottom, Try) => Random.nextInt(2) match
                    case 0 => pure(Right)
                    case 1 => pure(Left)


                case (Right, Reset) => pure(Right)
                case (Right, Try) => Random.nextInt(2) match
                    case 0 => pure(Bottom)
                    case _ => pure(Left)


                case (Left, Reset) => pure(Left)
                case (Left, Try) => Random.nextInt(2) match
                    case 0 => pure(Bottom)
                    case _ => pure(Top)


                case (Top, Reset) => pure(Top)
                case (Top, Try) => pure(Bottom)

                case _ => pure(Bottom)

            _ <- modify(_.setDirection(newDir(0), newDir(1)).asInstanceOf[MovementEntity])
        yield
            ()


    /**
     * Checks if the entity can move based on the current state direction .
     *
     * @param scaleFactor The scale factor for movement check.
     * @return A State datatype with the option of the next movement direction.
     */
    private def checkMove(scaleFactor: Double): State[MovementEntity, Option[(Double, Double)]] =
        for
            s0 <- getState
            x <- gets(e => (e.directionX, e.directionY))
            if s0.testMoveRelative(x(0) / scaleFactor, x(1) / scaleFactor)
        yield x

    /**
     * Moves to the next valid position considering the remaining iterations and scale factor.
     *
     * @param remainingIterations The number of remaining iterations to attempt a move.
     * @param scaleFactor         The scale factor for movement.
     * @return A State datatype with the next valid movement direction.
     */
    private def moveNext(remainingIterations: Int, scaleFactor: Double): State[MovementEntity, (Double, Double)] =
        for
            c <- checkMove(scaleFactor)
            d <- c match
                case Some(d) => pure(d)
                case None if remainingIterations > 0 =>
                    transition(Try).flatMap(_ => moveNext(remainingIterations-1, scaleFactor))
                case _ => pure(NoDirection)
        yield d

    /**
     * Computes the state by first reset transitioning to a state and then try for the next valid position.
     *
     * @param maxIteration The maximum number of iterations to attempt a move.
     * @param scaleFactor  The scale factor for movement.
     * @return A State monad with the computed movement direction.
     */
    def computeState(maxIteration: Int, scaleFactor: Double): State[MovementEntity,(Double, Double)] =
        for
            _ <- transition(Reset)
            d <- moveNext(maxIteration, scaleFactor)
        yield d

/**
 * object to provide utility methods for computing AI movement states.
 */
object AiMovementStateMachineUtils:
    private val MAX_ITERATION_BEFORE_DEFAULT = 5
    private val SCALE_FACTOR_MOVEMENT = 100D

    /**
     * Computes the AI state for movement based on the provided entity and parameters.
     *
     * @param entity       The movement entity.
     * @param maxIteration The maximum number of iterations to attempt a move.
     * @param movementBias The scale factor to apply for movement check.
     * @return A tuple containing the computed movement direction and the updated movement entity.
     */
    def computeAiState(entity: MovementEntity, maxIteration: Int = MAX_ITERATION_BEFORE_DEFAULT, movementBias: Double = SCALE_FACTOR_MOVEMENT): ((Double, Double), MovementEntity) =
        AiMovementStateMachine.computeState(maxIteration, movementBias).runAndReturn(entity)

