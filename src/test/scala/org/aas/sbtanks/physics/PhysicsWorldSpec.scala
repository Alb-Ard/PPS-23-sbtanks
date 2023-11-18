package org.aas.sbtanks.physics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PhysicsWorldSpec extends AnyFlatSpec with Matchers:
    "The Physics world" should "be able to register a collider" in {
        PhysicsWorld.clearColliders()
        val mockCollider = MockCollider(0, 0, 0, 0, CollisionLayer.TanksLayer)
        noException should be thrownBy PhysicsWorld.registerCollider(mockCollider)
        PhysicsWorld.hasCollider(mockCollider) should be (true)
    }
    
    it should "be able to unregister a collider" in {
        PhysicsWorld.clearColliders()
        val mockCollider = MockCollider(0, 0, 0, 0, CollisionLayer.TanksLayer)
        PhysicsWorld.registerCollider(mockCollider)
        assume (PhysicsWorld.hasCollider(mockCollider))
        noException should be thrownBy PhysicsWorld.unregisterCollider(mockCollider)
        PhysicsWorld.hasCollider(mockCollider) should be (false)
    }

    it should "be able to detect colliders overlapping a given box" in {
        PhysicsWorld.clearColliders()
        val expectedColliders = Seq(
            MockCollider(0, 0, 10, 10, CollisionLayer.TanksLayer),
            MockCollider(10, 10, 10, 10, CollisionLayer.TanksLayer),
        )
        expectedColliders.foreach(PhysicsWorld.registerCollider)
        val nonExpectedColliders = Seq(
            MockCollider(-5, -5, 3, 3, CollisionLayer.TanksLayer),
            MockCollider(20, 20, 5, 5, CollisionLayer.TanksLayer),
        )
        nonExpectedColliders.foreach(PhysicsWorld.registerCollider)
        val foundColliders = PhysicsWorld.getBoxOverlaps(AABB(0, 0, 15, 15), Seq(CollisionLayer.TanksLayer), Seq.empty) 
        expectedColliders.foreach(c => foundColliders should contain (c))
        nonExpectedColliders.foreach(c => foundColliders should not contain (c))
    }

    it should "not detect any colliders overlapping a given box if checking with no layers" in {
        PhysicsWorld.clearColliders()
        val mockCollider = MockCollider(0, 0, 10, 10, CollisionLayer.TanksLayer)
        PhysicsWorld.getBoxOverlaps(AABB(0, 0, 5, 5), Seq.empty, Seq.empty) should be (empty)
    }

    it should "not detect a collider overlapping a given box if checking on other layers" in {
        PhysicsWorld.clearColliders()
        val mockCollider = MockCollider(0, 0, 10, 10, CollisionLayer.WallsLayer)
        PhysicsWorld.getBoxOverlaps(AABB(0, 0, 5, 5), Seq(CollisionLayer.TanksLayer), Seq.empty) should be (empty)
    }

    it should "not detect a collider overlapping a given box if it is in the ignore sequence" in {
        PhysicsWorld.clearColliders()
        val mockCollider = MockCollider(0, 0, 10, 10, CollisionLayer.WallsLayer)
        PhysicsWorld.getBoxOverlaps(AABB(0, 0, 5, 5), CollisionLayer.values.toSeq, Seq(mockCollider)) should be (empty)
    }
