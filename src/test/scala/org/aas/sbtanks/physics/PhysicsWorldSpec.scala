package org.aas.sbtanks.physics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class physicsSpec extends AnyFlatSpec with Matchers:
    "The Physics world" should "be able to register a collider" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        val mockCollider = MockCollider(0, 0, 0, 0, CollisionLayer.TanksLayer)
        noException should be thrownBy physics.registerCollider(mockCollider)
        physics.hasCollider(mockCollider) should be (true)
    }
    
    it should "be able to unregister a collider" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        val mockCollider = MockCollider(0, 0, 0, 0, CollisionLayer.TanksLayer)
        physics.registerCollider(mockCollider)
        assume (physics.hasCollider(mockCollider))
        noException should be thrownBy physics.unregisterCollider(mockCollider)
        physics.hasCollider(mockCollider) should be (false)
    }

    it should "be able to detect colliders overlapping a given box" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        val expectedColliders = Seq(
            MockCollider(0, 0, 10, 10, CollisionLayer.TanksLayer),
            MockCollider(10, 10, 10, 10, CollisionLayer.TanksLayer),
        )
        expectedColliders.foreach(physics.registerCollider)
        val nonExpectedColliders = Seq(
            MockCollider(-5, -5, 3, 3, CollisionLayer.TanksLayer),
            MockCollider(20, 20, 5, 5, CollisionLayer.TanksLayer),
        )
        nonExpectedColliders.foreach(physics.registerCollider)
        val foundColliders = physics.getBoxOverlaps(AABB(0, 0, 15, 15), Seq(CollisionLayer.TanksLayer), Seq.empty) 
        expectedColliders.foreach(c => foundColliders should contain (c))
        nonExpectedColliders.foreach(c => foundColliders should not contain (c))
    }

    it should "not detect any colliders overlapping a given box if checking with no layers" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        val mockCollider = MockCollider(0, 0, 10, 10, CollisionLayer.TanksLayer)
        physics.getBoxOverlaps(AABB(0, 0, 5, 5), Seq.empty, Seq.empty) should be (empty)
    }

    it should "not detect a collider overlapping a given box if checking on other layers" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        val mockCollider = MockCollider(0, 0, 10, 10, CollisionLayer.WallsLayer)
        physics.getBoxOverlaps(AABB(0, 0, 5, 5), Seq(CollisionLayer.TanksLayer), Seq.empty) should be (empty)
    }

    it should "not detect a collider overlapping a given box if it is in the ignore sequence" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        val mockCollider = MockCollider(0, 0, 10, 10, CollisionLayer.WallsLayer)
        physics.getBoxOverlaps(AABB(0, 0, 5, 5), CollisionLayer.values.toSeq, Seq(mockCollider)) should be (empty)
    }
