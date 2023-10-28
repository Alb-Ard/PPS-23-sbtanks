package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.behaviours.{PositionBehaviour, SteppedMovementDirectionBehaviour}

//trait TankShootingBehaviour(speedData: Int, moveInfo: (Double, Double),
//                                posX: Double, posY: Double):
trait TankShootingBehaviour(speedData: Int, direction: SteppedMovementDirectionBehaviour,
                            posX: Double, posY: Double):
    self: PositionBehaviour with SteppedMovementDirectionBehaviour

    private var position: (Double, Double) = (posX, posY)

    def shoot(): Bullet with SteppedMovementDirectionBehaviour =
        Bullet(speedData, direction.direction, position)  with direction