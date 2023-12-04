package org.aas.sbtanks.entities.tank.behaviours

import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour, MovementBehaviour}
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.PhysicsContainer

@deprecated
trait TankShootingBehaviour:
    this: Tank with PositionBehaviour with DirectionBehaviour =>

    def shoot(using physics: PhysicsContainer)() =
        new Bullet(tankData.bulletSpeed, false)
            with PositionBehaviour(positionX + directionX, positionY + directionY)
            with MovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(1, 1, CollisionLayer.BulletsLayer,
                Seq(CollisionLayer.BulletsLayer, CollisionLayer.TanksLayer, CollisionLayer.WallsLayer))
            with DamageableBehaviour:
                override def applyDamage(amount: Int) = 
                    destroyed(())
                    this