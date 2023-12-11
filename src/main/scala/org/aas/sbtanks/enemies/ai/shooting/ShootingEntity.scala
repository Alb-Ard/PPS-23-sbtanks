package org.aas.sbtanks.enemies.ai.shooting

import org.aas.sbtanks.behaviours.{DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour

/**
 * Represents an entity that exhibits shooting behavior, has a position, and supports
 * line of sight functionality.
 */
type ShootingEntity = DirectionBehaviour
    with TankMultipleShootingBehaviour
    with PositionBehaviour
    with LineOfSight
    
