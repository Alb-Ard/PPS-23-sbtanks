package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.behaviours.BulletMovementBehaviour

trait TankShootingBehaviour(speedData: Int, moveInfo: (Double, Double),
                            posX: Double, posY: Double):

    private var position: (Double, Double) = (posX, posY)

    def shoot(): Bullet =
        Bullet(speedData, moveInfo, position)