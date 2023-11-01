package org.aas.sbtanks.obstacles

import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour

case class LevelObstacle(val imagesPath: Seq[String])

object LevelObstacle:
    sealed trait LevelObstacleProvider(val imagesPath: String*):
        def apply(x: Double, y: Double) = 
            Seq(new LevelObstacle(imagesPath) with PositionBehaviour(x, y))

    sealed trait CollidableLevelObstacle(val sizeX: Double, val sizeY: Double, val layer: CollisionLayer):
        this: LevelObstacleProvider =>

        override def apply(x: Double, y: Double) =
            Seq(new LevelObstacle(imagesPath) with PositionBehaviour(x, y) with CollisionBehaviour(sizeX, sizeY, layer, Seq.empty))

    sealed trait DestroyableLevelObstacle(val edgeSubsections: Int, val imagesAsAnimation: Boolean):
        this: CollidableLevelObstacle with LevelObstacleProvider =>

        override def apply(x: Double, y: Double) =
            (0 until edgeSubsections)
                .flatMap(subsectionY => (0 until edgeSubsections)
                    .map(subsectionX => (subsectionX.asInstanceOf[Double], subsectionY.asInstanceOf[Double])))
                .map((subsectionX, subsectionY) => 
                    new LevelObstacle(imagesAsAnimation match
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
                )

    case object BrickWall extends LevelObstacleProvider("obstacles/brick_wall_1.png", "obstacles/brick_wall_2.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)
        with DestroyableLevelObstacle(4, false)

    case object SteelWall extends LevelObstacleProvider("obstacles/steel_wall.png") 
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)

    case object Trees extends LevelObstacleProvider("obstacles/trees.png")

    case object Water extends LevelObstacleProvider("obstacles/water_1.png", "obstacles/water_2.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.NonWalkableLayer)
    
    case object IndestructibleWall extends LevelObstacleProvider("obstacles/indestructible_wall.png")
        with CollidableLevelObstacle(1, 1, CollisionLayer.WallsLayer)