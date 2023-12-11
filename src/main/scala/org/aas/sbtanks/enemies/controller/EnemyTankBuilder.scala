package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.shooting.LineOfSight
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder.PRIORITY_TARGET_PREDICATE
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.tank.factories.{BasicTankData, TankTypeData}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.physics.{Collider, CollisionLayer, PhysicsContainer}
import org.aas.sbtanks.player.{PlayerTank, PlayerTankBuilder}

/**
 * Case class for building instances of enemy tanks with various configuration options.
 *
 * @param x               The initial x-coordinate of the tank. Default is 0.
 * @param y               The initial y-coordinate of the tank. Default is 0.
 * @param tankType        The type of tank to be created. Default is BasicTankData.
 * @param collisionSizeX  The x-size of the tank for collision detection. Default is 1.
 * @param collisionSizeY  The y-size of the tank for collision detection. Default is 1.
 * @param collisionLayer  The collision layer of the tank. Default is CollisionLayer.TanksLayer.
 * @param collisionMask   The set of collision layers for collision masking. Default is the default mask from PlayerTankBuilder.
 * @param isCharged       Indicates whether the tank is charged. Default is false.
 * @param seeThoughBlocks The see-through block distance for line of sight. Default is 8.
 */
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
            with LineOfSight(PRIORITY_TARGET_PREDICATE)(Seq(CollisionLayer.WallsLayer), Seq.empty, seeThoughBlocks, collisionSizeX / 2)
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

    /**
     * Priority target predicate for collision masking.
     *
     * @param collider The collider to check.
     * @return True if the collider is a LevelObstacle which is also the player base else False
     */
    val PRIORITY_TARGET_PREDICATE: Collider => Boolean =
        case levelObstacle: LevelObstacle if levelObstacle.obstacleType == LevelObstacle.PlayerBase => true
        case _ => false

    /**
     * Default collision mask for the enemy tanks
     */
    val DEFAULT_COLLISION_MASK: Set[CollisionLayer] = Set(
        CollisionLayer.WallsLayer,
        CollisionLayer.NonWalkableLayer,
        CollisionLayer.TanksLayer
    )
