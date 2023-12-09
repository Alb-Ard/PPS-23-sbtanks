package org.aas.sbtanks.enemies.ai.fsm.shooting

import org.aas.sbtanks.enemies.ai.State.State
import org.aas.sbtanks.enemies.ai.fsm.shooting.FocusPolicy.*
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import org.aas.sbtanks.physics.Collider

/**
 * object representing a state machine for for AI shooting focus behaviour.
 * Extends AbstractStateMachine with ShootingEntity and FocusPolicy types.
 */
object AiFocusShootingStateMachine extends AbstractStateMachine[ShootingEntity, FocusPolicy]:

    /**
     * Transitions the state based on the given transition.
     *
     * @param value The focus policy for the shooting entity.
     * @return A State datatype representing the transition.
     */
    override def transition(value: FocusPolicy): State[ShootingEntity, Unit] =
        for
            target <- gets(_.findFirstDirectionCollider())
            newDir <- (target, value) match
                case ((Some(_), targetDir), Idle) => pure(targetDir)
                case _ => gets(x => (x.directionX, x.directionY))

            _ <- modify(_.setDirection(newDir(0), newDir(1)).asInstanceOf[ShootingEntity])
        yield
            ()

    /**
     * Checks for the actual entity and returns the target colliders along its direction as a state operation.
     *
     * @return A monadic state returning an optional set of target collider.
     */
    private def checkForTarget(): State[ShootingEntity, Option[Seq[Collider]]] =
        for
            inFocus <- gets(e => e.getCollidersOn(e.directionX, e.directionY))
            if inFocus.nonEmpty
        yield
            inFocus

    /**
     * Checks if the target is actually in focus for a target and transitions the state accordingly.
     *
     * @return A state representing the check for the target in focus returning a boolean value.
     */
    def isTargetInFocus: State[ShootingEntity, Boolean] =
        for
            focus <- checkForTarget()
            _ <- focus match
                case Some(_) => transition(Focus)
                case _ => transition(Idle)
            newFocus <- checkForTarget()
        yield
            newFocus.isDefined

/**
 * object to provide utility methods for computing AI shooting focus states.
 */
object AiShootingStateMachineUtils:
    def fixedOnPriorityTarget(entity: ShootingEntity): Option[ShootingEntity] =
        Option(AiFocusShootingStateMachine.isTargetInFocus.runAndReturn(entity))
            .withFilter:
                case (hasFocus, _) => !hasFocus
            .map:
                case (_, newState) => newState



