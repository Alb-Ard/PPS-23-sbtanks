package org.aas.sbtanks.entities.powerups.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp


type PickablePowerUp = PowerUp[_] with PositionBehaviour with CollisionBehaviour
                            