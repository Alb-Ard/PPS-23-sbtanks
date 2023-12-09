package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.shooting.LineOfSight
import org.aas.sbtanks.entities.tank.factories.{BasicTankData, TankTypeData}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.player.{PlayerTank, PlayerTankBuilder}
import org.aas.sbtanks.physics.PhysicsContainer

case class EnemyTankBuilder(x: Double = 0,
                            y: Double = 0,
                            tankType: TankTypeData = BasicTankData,
                            collisionSizeX: Double = 1,
                            collisionSizeY: Double = 1,
                            collisionLayer: CollisionLayer = CollisionLayer.TanksLayer,
                            collisionMask: Set[CollisionLayer] = PlayerTankBuilder.DEFAULT_COLLISION_MASK,
                            isCharged: Boolean = false,
                            seeThoughBlocks: Int = 8
                           ):

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

    def setCharged(value: Boolean) =
        copy(isCharged = value)

    def setSeeThorughBlocks(amount: Int) =
        copy(seeThoughBlocks = amount)

    def build(using physics: PhysicsContainer)() =
        new Tank(tankType)
            with PositionBehaviour(x, y)
            with ConstrainedMovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(collisionSizeX, collisionSizeY, collisionLayer, collisionMask.toSeq)
            with LineOfSight(Seq(CollisionLayer.WallsLayer), Seq.empty, seeThoughBlocks)
            with DamageableBehaviour:
            override protected def applyDamage(source: Any, amount: Int) =
                updateTankData(tankData.updateHealth(_ - 1))
                tankData.health match
                    case v if v <= 0 =>
                        destroy(())
                        this
                    case _ => this
        .setCharged(isCharged)



object EnemyTankBuilder:
    val DEFAULT_COLLISION_MASK: Set[CollisionLayer] = Set(
        CollisionLayer.WallsLayer,
        CollisionLayer.NonWalkableLayer,
        CollisionLayer.TanksLayer
    )
