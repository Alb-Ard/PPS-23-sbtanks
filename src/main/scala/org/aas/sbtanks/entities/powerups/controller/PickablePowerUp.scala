package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp

/**
 * type alias to provide a a PowerUp extended with Position and Collision behaviour
 * @tparam E
 */
type PickablePowerUp[E] = PowerUp[E] with PositionBehaviour with CollisionBehaviour

