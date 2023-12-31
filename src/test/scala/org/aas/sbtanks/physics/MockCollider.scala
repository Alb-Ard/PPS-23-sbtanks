package org.aas.sbtanks.physics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.physics.PhysicsContainer
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour

class MockCollider(x: Double, y: Double, sizeX: Double, sizeY: Double, override val layer: CollisionLayer)(using PhysicsContainer)
    extends PositionBehaviour(x, y) with CollisionBehaviour(sizeX, sizeY, layer, CollisionLayer.values.toSeq)

class MockDirectionalCollider(x: Double, y: Double, sizeX: Double, sizeY: Double, override val layer: CollisionLayer)(using PhysicsContainer)
    extends PositionBehaviour(x, y) with CollisionBehaviour(sizeX, sizeY, layer, CollisionLayer.values.toSeq) with DirectionBehaviour
