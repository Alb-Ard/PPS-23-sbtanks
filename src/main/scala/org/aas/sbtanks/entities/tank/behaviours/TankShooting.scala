package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.tank.structure.Tank

//trait TankShootingBehaviour(speedData: Int, moveInfo: (Double, Double),
//                                posX: Double, posY: Double):
trait TankShootingBehaviour:
    self: Tank with PositionBehaviour with DirectionBehaviour =>

    def shoot(): Bullet with PositionBehaviour with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour =
        new Bullet(self.tankData.bulletSpeed, false) with PositionBehaviour(self.positionX + self.directionX, self.positionY + self.directionY)
                    with DirectionBehaviour with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                                            Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
                    with DamageableBehaviour:
                        override def damage(): Unit = -1