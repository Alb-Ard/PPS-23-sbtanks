package org.aas.sbtanks.physics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour

class RayCastSpec extends AnyFlatSpec with Matchers:
    case class MockCollider(x: Double, y: Double, sizeX: Double, sizeY: Double, override val layer: CollisionLayer) 
        extends PositionBehaviour(x, y) with CollisionBehaviour(sizeX, sizeY, layer, CollisionLayer.ALL_LAYERS)

    "A horizontal RayCast" should "not return any collisions in any empty world" in {
        PhysicsWorld.clearColliders()
        PhysicsWorld.horizontalRayCast(0, 0, Option.empty, CollisionLayer.ALL_LAYERS, Seq.empty) shouldBe empty
    }

    it should "not return any collisions when no objects are in its check area" in {
        PhysicsWorld.clearColliders()
        PhysicsWorld.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        PhysicsWorld.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        PhysicsWorld.horizontalRayCast(0, 0, Option.empty, CollisionLayer.ALL_LAYERS, Seq.empty) shouldBe empty
    }

    it should "return collisions when objects are in its check area" in {
        PhysicsWorld.clearColliders()
        PhysicsWorld.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        val colliderInFront = MockCollider(10, 0, 1, 1, CollisionLayer.TanksLayer)
        PhysicsWorld.registerCollider(colliderInFront)
        PhysicsWorld.horizontalRayCast(0, 0, Option.empty, CollisionLayer.ALL_LAYERS, Seq.empty) shouldBe empty
    }

    "A vertical RayCast" should "not return any collisions in any empty world" in {
        PhysicsWorld.clearColliders()
        PhysicsWorld.verticalRayCast(0, 0, Option.empty, CollisionLayer.ALL_LAYERS, Seq.empty) shouldBe empty
    }

    it should "not return any collisions when no objects are in its check area" in {
        PhysicsWorld.clearColliders()
        PhysicsWorld.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        PhysicsWorld.registerCollider(MockCollider(0, -10, 1, 1, CollisionLayer.TanksLayer))
        PhysicsWorld.verticalRayCast(0, 0, Option.empty, CollisionLayer.ALL_LAYERS, Seq.empty) shouldBe empty
    }

    it should "return collisions when objects are in its check area" in {
        PhysicsWorld.clearColliders()
        PhysicsWorld.registerCollider(MockCollider(-10, 0, 1, 1, CollisionLayer.TanksLayer))
        val colliderInFront = MockCollider(0, 10, 1, 1, CollisionLayer.TanksLayer)
        PhysicsWorld.registerCollider(colliderInFront)
        PhysicsWorld.verticalRayCast(0, 0, Option.empty, CollisionLayer.ALL_LAYERS, Seq.empty) should contain (colliderInFront)
    }