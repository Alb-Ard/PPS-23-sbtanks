package org.aas.sbtanks.level.scalafx

import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.aas.sbtanks.level.LevelFactory
import scalafx.scene.Node
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import org.aas.sbtanks.level.LevelFactory.StringEntity
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringBrickWall
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringSteelWall
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringWater
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringTrees
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringBase
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringIce
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringIndestructibleWall
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringPlayer
import org.aas.sbtanks.level.LevelFactory.StringEntity.StringEmpty
import org.aas.sbtanks.player.PlayerTankBuilder
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.physics.PhysicsContainer

final case class JFXLevelFactory(tileSize: Double, viewScale: Double, tileAnimationSpeed: Double, tankAnimationSpeed: Double)(using physics: PhysicsContainer) extends LevelFactory[AnyRef, Node]:
    val TANK_SPRITE_SIZE = (13, 14)

    val pixelSize = 1D / tileSize

    override protected def createEntityMv(entity: StringEntity, x: Double, y: Double): Seq[(AnyRef, Node)] = 
        entity match
            case StringBrickWall => createObstaclesMv(LevelObstacle.BrickWall(x, y))
            case StringSteelWall => createObstaclesMv(LevelObstacle.SteelWall(x, y))
            case StringWater => createAnimatedObstaclesMv(LevelObstacle.Water(x, y))
            case StringTrees => createObstaclesMv(LevelObstacle.Trees(x, y))
            case StringBase => createObstaclesMv(LevelObstacle.PlayerBase(x, y))
            case StringIce => Seq.empty // Ice is not implemented
            case StringPlayer => createTankMv(x, y, "player", Seq("slow", "basic"))
            case StringIndestructibleWall => createObstaclesMv(LevelObstacle.IndestructibleWall(x, y))
            case StringEmpty => Seq.empty
    
    private def createObstaclesMv(obstacles: Seq[LevelObstacle]) = obstacles.map(o => ((
            o,
            JFXObstacleView.create(JFXImageLoader.loadFromResources(o.imagesPath(0), tileSize / Math.sqrt(obstacles.size), viewScale))
        )))

    private def createAnimatedObstaclesMv(obstacles: Seq[LevelObstacle]) = obstacles.map(o => ((
            o,
            JFXObstacleView.createAnimated(JFXImageLoader.loadFromResources(o.imagesPath, tileSize, viewScale), tileAnimationSpeed)
        )))

    private def createTankMv(x: Double, y: Double, tankType: String, tankAttributes: Seq[String]) =
        val tank = PlayerTankBuilder()
                .setPosition(x, y)
                .setCollisionSize(x = TANK_SPRITE_SIZE(0) * pixelSize, y = TANK_SPRITE_SIZE(1) * pixelSize)
                .setCollisionOffset(pixelSize, pixelSize)
                .build()
        val attributeString = tankAttributes.fold("")((c, n) => c + n + "_")
        val images = Seq("up", "right", "down", "left").map(d => JFXImageLoader.loadFromResources(Seq(
                    s"entities/tank/$tankType/${tankType}_tank_${attributeString}${d}_1.png", 
                    s"entities/tank/$tankType/${tankType}_tank_${attributeString}${d}_2.png"),
                tileSize - pixelSize,
                tileSize,
                viewScale))
        val tankView = JFXTankView(images, tankAnimationSpeed)
        Seq((tank, tankView))
