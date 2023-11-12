package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.behaviours.{CollisionBehaviour, MovementBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer

trait TankMultipleShootingBehaviour:
    self: Tank with PositionBehaviour with DirectionBehaviour =>

    def shoot(nShots: Int) =
        var shotsFired: Seq[Bullet with PositionBehaviour with MovementBehaviour
            with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour] = Seq.empty
        for(n <- Range(1, nShots + 1))
            shotsFired = shotsFired :+ generateBullet((self.directionX * n, self.directionY * n))
            //shotsFired.::(generateBullet((self.directionX * n, self.directionY * n)))
            //Seq(generateBullet((self.directionX, self.directionY)), generateBullet((self.directionX * 2, self.directionY * 2)))
        shotsFired


    private def generateBullet(offset: (Double, Double)) =
        new Bullet(self.tankData.bulletSpeed, false)
            with PositionBehaviour(self.positionX + offset._1, self.positionY + offset._2)
            with MovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
            with DamageableBehaviour:
                override def applyDamage(amount: Int) =
                    destroyed(())
                    this

