package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.structure.Tank

type MovementEntity = DirectionBehaviour
    with PositionBehaviour
    with CollisionBehaviour
    with ConstrainedMovementBehaviour