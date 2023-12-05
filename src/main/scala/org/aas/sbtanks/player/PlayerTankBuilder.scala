package org.aas.sbtanks.player

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.entities.tank.TankExample.updatedTank
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.physics.PhysicsContainer

case class PlayerTank() extends Tank(PlayerTankData)

case class PlayerTankBuilder(private val x: Double = 0, 
    private val y: Double = 0,
    private val collisionSizeX: Double = 1,
    private val collisionSizeY: Double = 1,
    private val collisionOffsetX: Double = 0,
    private val collisionOffsetY: Double = 0,
    private val collisionLayer: CollisionLayer = CollisionLayer.TanksLayer,
    private val collisionMask: Set[CollisionLayer] = PlayerTankBuilder.DEFAULT_COLLISION_MASK):

    def setPosition(x: Double = x, y: Double = y) =
        copy(x = x, y = y)

    def setCollisionSize(x: Double = x, y: Double = y) =
        copy(collisionSizeX = x, collisionSizeY = y)

    def setCollisionOffset(x: Double = x, y: Double = y) =
        copy(collisionOffsetX = x, collisionOffsetY = y)

    def setCollisionLayer(layer: CollisionLayer) =
        copy(collisionLayer = layer)

    def setCollisionMaskLayer(layer: CollisionLayer, active: Boolean = true) =
        copy(collisionMask = active match
            case true => collisionMask + layer
            case _ => collisionMask.filterNot(layer.equals)
        )

    def build(using physics: PhysicsContainer)() =
        new PlayerTank()
            with PositionBehaviour(x, y)
            with ConstrainedMovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(collisionSizeX, collisionSizeY, collisionLayer, collisionMask.toSeq)
            with DamageableBehaviour
            with TankMultipleShootingBehaviour:
                override protected def applyDamage(amount: Int) = 
                    updateTankData(tankData.updateHealth(_ - 1))
                    tankData.health match
                        case v if v <= 0 =>
                            destroyed(())
                            this
                        case _ =>
                            respawn()
                            this
                def respawn() =
                    this.setPosition(startingX, startingY)
        .setBoundingBoxOffset(collisionOffsetX, collisionOffsetY)

object PlayerTankBuilder:
    val DEFAULT_COLLISION_MASK: Set[CollisionLayer] = Set(
        CollisionLayer.WallsLayer,
        CollisionLayer.NonWalkableLayer
    )