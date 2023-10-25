package org.aas.sbtanks.physics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PhysicsWorldSpec extends AnyFlatSpec with Matchers:
    "The Physics world" should "be able to register a collider" in {
        val mockCollider = new Object() with Collider {
            override def boundingBox: AABB = AABB(0, 0, 0, 0)
            override val layer: CollisionLayer = CollisionLayer.TanksLayer
            override val layerMasks: Seq[CollisionLayer] = Seq.empty
        }
        noException should be thrownBy PhysicsWorld.registerCollider(mockCollider)
        PhysicsWorld.hasCollider(mockCollider) should be (true)
    }
    
    "The Physics world" should "be able to unregister a collider" in {
        val mockCollider = new Object() with Collider {
            override def boundingBox: AABB = AABB(0, 0, 0, 0)
            override val layer: CollisionLayer = CollisionLayer.TanksLayer
            override val layerMasks: Seq[CollisionLayer] = Seq.empty
        }
        PhysicsWorld.registerCollider(mockCollider)
        assume(PhysicsWorld.hasCollider(mockCollider))
        noException should be thrownBy PhysicsWorld.unregisterCollider(mockCollider)
    }
