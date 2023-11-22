package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer

trait TankMultipleShootingBehaviour:
    self: Tank with PositionBehaviour with DirectionBehaviour =>

    def shoot(nShots: Int, isPlayerBullet: Boolean) =
        var shotsFired: Seq[Bullet with PositionBehaviour with MovementBehaviour
            with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour] = Seq.empty
        for(n <- Range(1, nShots + 1))
            shotsFired = shotsFired :+ generateBullet((self.directionX * n, self.directionY * n), isPlayerBullet)
        shotsFired.foreach(b => b.setDirection(self.directionX, self.directionY))
        shotsFired


    private def generateBullet(offset: (Double, Double), isPlayerBullet: Boolean) =
        new Bullet(self.tankData.bulletSpeed, isPlayerBullet)
            with PositionBehaviour(self.positionX + offset._1, self.positionY + offset._2)
            with MovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(0.5, 0.5, CollisionLayer.BulletsLayer,
                Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer, CollisionLayer.NonWalkableLayer))
            with DamageableBehaviour:
                override def applyDamage(amount: Int) =
                    destroyed(())
                    this