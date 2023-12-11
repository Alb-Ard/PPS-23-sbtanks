package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.structure.Tank

/**
 * Represents an entity that exhibits a movement behavior, has a position and a direction, and a collision behaviour.
 */
type MovementEntity = DirectionBehaviour
    with PositionBehaviour
    with CollisionBehaviour
    with ConstrainedMovementBehaviour