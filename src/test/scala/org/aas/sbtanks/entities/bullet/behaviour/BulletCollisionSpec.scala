package org.aas.sbtanks.entities.bullet.behaviour

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.behaviours.TankShootingBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class BulletCollisionSpec extends AnyFlatSpec with Matchers{
    import org.aas.sbtanks.entities.bullet.Bullet

    val bullet1 = new Bullet(1, false) with PositionBehaviour with DirectionBehaviour
                                with CollisionBehaviour with DamageableBehaviour
    val bullet2 = new Bullet(1, false) with PositionBehaviour with DirectionBehaviour
                                with CollisionBehaviour with DamageableBehaviour

    "a bullet" should "be destroyed when hitting another bullet" in{

    }
}
