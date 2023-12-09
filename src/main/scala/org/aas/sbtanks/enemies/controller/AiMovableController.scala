package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.MovementEntity
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachineUtils.computeAiState
import org.aas.sbtanks.enemies.ai.fsm.shooting.AiShootingStateMachineUtils.fixedOnPriorityTarget
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import org.aas.sbtanks.entities.tank.structure.Tank

/**
 * Trait representing a controller for an AI movable entity with tank, movement, and shooting behaviors.
 *
 * @param entity  The AI-controlled entity, which is expected to be a Tank with MovementEntity and ShootingEntity traits.
 * @param offset  The offset value used in AI movement computations.
 * @param speedSupplier   A supplier function to provide the speed at which the entity should move.
 */
trait AiMovableController(val entity: Tank with MovementEntity with ShootingEntity, val offset: Double, val speedSupplier: () => Double):

    /**
     * Computes the new movement state for the AI-controlled entity based on priority targets and applies the movement.
     *
     * The method uses the fixedOnPriorityTarget function and wether the is no priority target to focus on,
     * it updates the entity's movement accordingly.
     */
    def computeNewMovementState() =
    
        fixedOnPriorityTarget(entity)
            .map(e => computeAiState(e.asInstanceOf[MovementEntity], movementBias = offset))
            .foreach((d, e) => e.moveRelative(d(0) * speedSupplier() * (1 / offset), d(1) * speedSupplier() * (1 / offset)))
        this






