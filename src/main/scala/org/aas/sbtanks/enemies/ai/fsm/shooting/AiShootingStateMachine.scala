package org.aas.sbtanks.enemies.ai.fsm.shooting

import org.aas.sbtanks.enemies.ai.State.State
import org.aas.sbtanks.enemies.ai.fsm.shooting.FocusPolicy.*
import org.aas.sbtanks.enemies.ai.fsm.{AbstractStateMachine, StateMachine, StateModifier}
import org.aas.sbtanks.enemies.ai.shooting.{AiShootingState, ShootingEntity}
import org.aas.sbtanks.physics.Collider

object AiShootingStateMachine extends AbstractStateMachine[ShootingEntity, FocusPolicy]:
    override def transition(value: FocusPolicy): State[ShootingEntity, Unit] =
        for
            target <- gets(_.findFirstDirectionCollider())
            newDir <- (target, value) match
                case ((Some(_), targetDir), Idle) => pure(targetDir)
                case _ => gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))

            _ <- modify(_.setDirection(newDir(0), newDir(1)).asInstanceOf[ShootingEntity])
        yield
            ()

    private def checkForTarget(): State[ShootingEntity, Option[Seq[Collider]]] =
        for
            inFocus <- gets(e => e.getCollidersOn(e.directionX, e.directionY))
            if inFocus.nonEmpty
        yield
            inFocus

    def isTargetInFocus: State[ShootingEntity, Boolean] =
        for
            focus <- checkForTarget()
            _ <- focus match
                case Some(_) => transition(Focus)
                case _ => transition(Idle)
            newFocus <- checkForTarget()
        yield
            newFocus.isDefined

object AiShootingStateMachineUtils:
    def checkAiPriorityTarget(entity: ShootingEntity): (Boolean, ShootingEntity) =
        AiShootingStateMachine.isTargetInFocus.runAndReturn(entity)



