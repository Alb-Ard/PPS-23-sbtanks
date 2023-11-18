package org.aas.sbtanks.enemies.ai.shooting

import org.aas.sbtanks.behaviours.{DirectionBehaviour, PositionBehaviour}

type ShootingEntity = DirectionBehaviour 
    with PositionBehaviour
    with LineOfSight
    
