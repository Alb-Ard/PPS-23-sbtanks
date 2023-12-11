package org.aas.sbtanks.physics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour

class RayCastSpec extends AnyFlatSpec with Matchers:
    "A horizontal RayCast" should "not return any collisions in any empty world" in {
        val physics = new Object() with PhysicsContainer 
        given PhysicsContainer = physics
        physics.horizontalRayCast(0, 0, Option.empty, CollisionLayer.values.toSeq, Seq.empty) should be (empty)
    }

    it should "be able to do a infinite negative check" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        val colliderInFront = MockCollider(-10, 1, 1, 1, CollisionLayer.TanksLayer)
        physics.registerCollider(colliderInFront)
        physics.horizontalRayCast(10, 1, Option(Raycast.INFINITE_NEGATIVE), CollisionLayer.values.toSeq, Seq.empty) should contain (colliderInFront)
    }

    it should "not return any collisions when no objects are in its check area" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        physics.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        physics.horizontalRayCast(0, 0, Option.empty, CollisionLayer.values.toSeq, Seq.empty) should be (empty)
    }

    it should "return collisions when objects are in its check area" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        val colliderInFront = MockCollider(10, 0, 1, 1, CollisionLayer.TanksLayer)
        physics.registerCollider(colliderInFront)
        val foundColliders = physics.horizontalRayCast(0, 0, Option.empty, CollisionLayer.values.toSeq, Seq.empty) 
        foundColliders.length should be (1)
        foundColliders should contain (colliderInFront)
    }

    it should "be able to cast in a negative direction" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        val colliderInFront = MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer)
        physics.registerCollider(colliderInFront)
        val foundColliders = physics.horizontalRayCast(0, 0, Option(-20), CollisionLayer.values.toSeq, Seq.empty) 
        foundColliders.length should be (1)
        foundColliders should contain (colliderInFront)
    }

    "A vertical RayCast" should "not return any collisions in any empty world" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.verticalRayCast(0, 0, Option.empty, CollisionLayer.values.toSeq, Seq.empty) should be (empty)
    }

    it should "be able to do a infinite negative check" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        val colliderInFront = MockCollider(3, 1, 1, 1, CollisionLayer.TanksLayer)
        physics.registerCollider(colliderInFront)
        physics.verticalRayCast(3, 3, Option(Raycast.INFINITE_NEGATIVE), CollisionLayer.values.toSeq, Seq.empty) should contain (colliderInFront)
    }

    it should "not return any collisions when no objects are in its check area" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        physics.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        physics.verticalRayCast(0, 0, Option.empty, CollisionLayer.values.toSeq, Seq.empty) should be (empty)
    }

    it should "return collisions when objects are in its check area" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        val colliderInFront = MockCollider(0, 10, 1, 1, CollisionLayer.TanksLayer)
        physics.registerCollider(colliderInFront)
        val foundColliders = physics.verticalRayCast(0, 0, Option.empty, CollisionLayer.values.toSeq, Seq.empty) 
        foundColliders.length should be (1)
        foundColliders should contain (colliderInFront)
    }

    it should "be able to cast in a negative direction" in {
        val physics = new Object() with PhysicsContainer  
        given PhysicsContainer = physics
        physics.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        val colliderInFront = MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer)
        physics.registerCollider(colliderInFront)
        val foundColliders = physics.verticalRayCast(0, 0, Option(-20), CollisionLayer.values.toSeq, Seq.empty) 
        foundColliders.length should be (1)
        foundColliders should contain (colliderInFront)
    }