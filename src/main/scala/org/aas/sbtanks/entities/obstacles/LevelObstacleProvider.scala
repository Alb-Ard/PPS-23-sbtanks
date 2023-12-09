package org.aas.sbtanks.entities.obstacles

import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.physics.PhysicsContainer

/**
  * Instance of an obstacle entity
  *
  * @param obstacleType The type of obstacle this entity was generated from
  * @param imagesPath The path of the image(s) this obstacle will show
  */
class LevelObstacle(val obstacleType: LevelObstacleType, val imagesPath: Seq[String])

/**
  * A trait for all obstacle factories. Contains shortcuts to all factories
  */
trait LevelObstacleType:
    val brickWall = LevelObstacle.BrickWall
    val steelWall = LevelObstacle.SteelWall
    val trees = LevelObstacle.Trees
    val water = LevelObstacle.Water
    val indestructibleWall = LevelObstacle.IndestructibleWall
    val playerBase = LevelObstacle.PlayerBase

/**
  * Object containing all level obstacle factories
  */
object LevelObstacle:
    sealed trait LevelObstacleProvider(val obstacleType: LevelObstacleType, val imagesPath: String*):
        def apply(using physics: PhysicsContainer)(x: Double, y: Double) = 
            Seq(new LevelObstacle(obstacleType, imagesPath) with PositionBehaviour(x, y))

    sealed trait CollidableLevelObstacle(val sizeX: Double, val sizeY: Double, val layer: CollisionLayer):
        this: LevelObstacleProvider =>

        override def apply(using physics: PhysicsContainer)(x: Double, y: Double) =
            Seq(new LevelObstacle(obstacleType, imagesPath) with PositionBehaviour(x, y) with CollisionBehaviour(sizeX, sizeY, layer, Seq.empty))

    sealed trait DestroyableLevelObstacle(val edgeSubsections: Int, val imagesAsAnimation: Boolean):
        this: CollidableLevelObstacle with LevelObstacleProvider =>

        override def apply(using physics: PhysicsContainer)(x: Double, y: Double) =
            (0 until edgeSubsections)
                .flatMap(subsectionY => (0 until edgeSubsections)
                    .map(subsectionX => (subsectionX.asInstanceOf[Double], subsectionY.asInstanceOf[Double])))
                .map((subsectionX, subsectionY) => 
                    new LevelObstacle(obstacleType, imagesAsAnimation match
                        case true => imagesPath
                        case _ => Seq(imagesPath((subsectionX + subsectionY).asInstanceOf[Int] % imagesPath.length))) 
                    with PositionBehaviour(
                        x + subsectionX / edgeSubsections.asInstanceOf[Double], 
                        y + subsectionY / edgeSubsections.asInstanceOf[Double]
                    )
                    with CollisionBehaviour(
                        sizeX / edgeSubsections.asInstanceOf[Double],
                        sizeY / edgeSubsections.asInstanceOf[Double],
                        layer,
                        Seq.empty
                    )
                    with DamageableBehaviour:
                        override protected def applyDamage(source: Any, amount: Int) =
                            destroy(())
                            this
                )
    
    case object BrickWall extends LevelObstacleType 
        with LevelObstacleProvider(BrickWall, "obstacles/brick_wall_1.png", "obstacles/brick_wall_2.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)
        with DestroyableLevelObstacle(4, false)

    case object SteelWall extends LevelObstacleType 
        with LevelObstacleProvider(SteelWall, "obstacles/steel_wall.png") 
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)

    case object Trees extends LevelObstacleType 
        with LevelObstacleProvider(Trees, "obstacles/trees.png")

    case object Water extends LevelObstacleType 
        with LevelObstacleProvider(Water, "obstacles/water_1.png", "obstacles/water_2.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.NonWalkableLayer)
    
    case object IndestructibleWall extends LevelObstacleType 
        with LevelObstacleProvider(IndestructibleWall, "obstacles/indestructible_wall.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)

    case object PlayerBase extends LevelObstacleType 
        with LevelObstacleProvider(PlayerBase, "obstacles/player_base_1.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)
        with DestroyableLevelObstacle(1, false)

    extension (obstacle: LevelObstacle with PositionBehaviour)
        /**
          * Finds all obstacles near this obstacle
          *
          * @param obstaclesProvider A function that returns a sequence of obstacles that will be searched
          * @return a sequence with the near obstacles
          */
        def getNearObstacles(obstaclesProvider: () => Seq[LevelObstacle with PositionBehaviour], distanceX: Double = 1, distanceY: Double = 1) =
            val epsilon = 0.001D
            val epsDistanceX = distanceX + epsilon
            val epsDistanceY = distanceY + epsilon
            obstaclesProvider().map(o => (o, (obstacle.positionX - o.positionX, obstacle.positionY - o.positionY)))
                .map((o, d) => (o, (d(0) * d(0) <= epsDistanceX * epsDistanceX, d(1) * d(1) <= epsDistanceY * epsDistanceY)))
                .filter((_, d) => (epsDistanceY > epsDistanceX) match
                    case true => d(1)
                    case _ => d(0)
                )
                .filter((o, _) => o != obstacle)
                .map(_(0))
