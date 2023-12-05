package org.aas.sbtanks.levels

import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.level.LevelFactory.StringEntity.{StringBase, StringBrickWall, StringEmpty, StringIce, StringIndestructibleWall, StringPlayer, StringSteelWall, StringTrees, StringWater}
import org.aas.sbtanks.level.LevelFactory.stringEntityFromChar
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.physics.{Collider, PhysicsContainer}
import org.aas.sbtanks.level.LevelFactory

import scala.compiletime.uninitialized

final case class MockLevelFactory(tankFactory: (x: Double, y: Double) => Tank)(using physics: PhysicsContainer) extends LevelFactory[AnyRef, Unit]:
    private var entity: AnyRef = uninitialized

    def createFromString(levelString: String, levelEdgeSize: Int) =
        val levelEntityStrings = levelString.map(stringEntityFromChar).filter(e => e.isDefined)
        for
            y <- (0 until levelEdgeSize)
            x <- (0 until levelEdgeSize)
        do
            val entityIndex = x + y * levelEdgeSize
            levelEntityStrings(entityIndex)
                .map(createEntityMv(_, x, y))
                .getOrElse(Seq.empty)
                .foreach((m, _) => m match
                    case c: Collider => physics.registerCollider(c)
                    case _ => ()
                )

        this

    def getMainEntity[X <: AnyRef]: X = entity.asInstanceOf[X]


    override protected def createEntityMv(entity: LevelFactory.StringEntity, x: Double, y: Double): Seq[(AnyRef, Unit)] =
        entity match
            case StringBrickWall => createObstaclesMv(LevelObstacle.BrickWall(x, y))
            case StringSteelWall => createObstaclesMv(LevelObstacle.SteelWall(x, y))
            case StringWater => createObstaclesMv(LevelObstacle.Water(x, y))
            case StringTrees => createObstaclesMv(LevelObstacle.Trees(x, y))
            case StringBase => createObstaclesMv(LevelObstacle.PlayerBase(x, y))
            case StringIce => Seq.empty // Ice is not implemented
            case StringPlayer => createTankMv(x, y)
            case StringIndestructibleWall => createObstaclesMv(LevelObstacle.IndestructibleWall(x, y))
            case StringEmpty => Seq.empty

    private def createObstaclesMv(obstacles: Seq[LevelObstacle]) = obstacles.map(o => ((o, ())))

    private def createTankMv(x: Double, y: Double) =
        entity = tankFactory(x, y)
        Seq((entity, ()))


