package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.shooting.LineOfSight
import org.aas.sbtanks.entities.tank.factories.{BasicTankData, TankTypeData}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.player.{PlayerTank, PlayerTankBuilder}

case class EnemyTankBuilder(private val x: Double = 0,
                             private val y: Double = 0,
                             private val tankType: TankTypeData = BasicTankData,
                             private val collisionSizeX: Double = 1,
                             private val collisionSizeY: Double = 1,
                             private val collisionLayer: CollisionLayer = CollisionLayer.TanksLayer,
                             private val collisionMask: Set[CollisionLayer] = PlayerTankBuilder.DEFAULT_COLLISION_MASK):

    def setPosition(x: Double = x, y: Double = y) =
        copy(x = x, y = y)

    def setCollisionSize(x: Double = x, y: Double = y) =
        copy(collisionSizeX = x, collisionSizeY = y)

    def setCollisionLayer(layer: CollisionLayer) =
        copy(collisionLayer = layer)

    def setCollisionMaskLayer(layer: CollisionLayer, active: Boolean = true) =
        copy(collisionMask = active match
            case true => collisionMask + layer
            case _ => collisionMask.filterNot(layer.equals)
        )

    def setTankType(tankTypeData: TankTypeData) =
        copy(tankType = tankTypeData)

    def build() =
        new Tank(tankType)
            with PositionBehaviour(x, y)
            with ConstrainedMovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(collisionSizeX, collisionSizeY, collisionLayer, collisionMask.toSeq)
            with LineOfSight(Seq(CollisionLayer.WallsLayer), Seq.empty)
            with DamageableBehaviour:
            override protected def applyDamage(amount: Int) =
                updateTankData(tankData.updateHealth(_ - 1))
                tankData.health match
                    case v if v <= 0 =>
                        destroyed(())
                        this
                    case _ => this


object EnemyTankBuilder:
    val DEFAULT_COLLISION_MASK: Set[CollisionLayer] = Set(
        CollisionLayer.BulletsLayer,
        CollisionLayer.WallsLayer,
        CollisionLayer.NonWalkableLayer,
    )
