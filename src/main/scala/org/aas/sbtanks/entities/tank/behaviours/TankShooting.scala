package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.TankData

trait TankShootingBehaviour(shooterTankData: TankData, moveInfo: SteppedMovementDirectionBehaviour,
                            posX: Double, posY: Double):

    private var position: (Double, Double) = (posX, posY)

    def shoot(): Bullet =
        new Bullet(shooterTankData.speed, moveInfo.direction, position)