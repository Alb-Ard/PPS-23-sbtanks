package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.tank.structure.Tank

//trait TankShootingBehaviour(speedData: Int, moveInfo: (Double, Double),
//                                posX: Double, posY: Double):

//private var position: (Double, Double) = (posX, posY)
trait TankShootingBehaviour:
    self: Tank with PositionBehaviour with DirectionBehaviour =>

    def shoot(): Bullet with PositionBehaviour with DirectionBehaviour with CollisionBehaviour =
        new Bullet(self.tankData.bulletSpeed) with PositionBehaviour(self.positionX + self.directionX, self.positionY + self.directionY)
                    with DirectionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                                            Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))