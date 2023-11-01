package org.aas.sbtanks.level.scalafx

import org.aas.sbtanks.level.LevelFactory
import scalafx.scene.Node
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.obstacles.view.scalafx.JFXObstacleView
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
import org.aas.sbtanks.player.PlayerTankBuilder
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView

final case class JFXLevelFactory(tileSize: Double, viewScale: Double, tileAnimationSpeed: Double) extends LevelFactory[AnyRef, Node]:
    override protected def createEntityVm(entity: StringEntity, x: Double, y: Double): Seq[(AnyRef, Node)] = 
        entity match
            case StringBrickWall => LevelObstacle.BrickWall(x, y).map(b => ((
                b,
                JFXObstacleView.create(JFXImageLoader.loadFromResources(b.imagesPath(0), tileSize / 4, viewScale))
            )))
            case StringSteelWall => LevelObstacle.SteelWall(x, y).map(s => ((
                s,
                JFXObstacleView.create(JFXImageLoader.loadFromResources(s.imagesPath(0), tileSize, viewScale))
            )))
            case StringWater => LevelObstacle.Water(x, y).map(w => ((
                w,
                JFXObstacleView.createAnimated(JFXImageLoader.loadFromResources(w.imagesPath, tileSize, viewScale), tileAnimationSpeed)
            )))
            case StringTrees => LevelObstacle.Trees(x, y).map(t => ((
                t,
                JFXObstacleView.create(JFXImageLoader.loadFromResources(t.imagesPath(0), tileSize, viewScale))
            )))
            case StringBase => ???
            case StringIce => ???
            case StringPlayer => 
                val player = PlayerTankBuilder().setPosition(x, y).build()
                val playerImages = JFXImageLoader.loadFromResources(Seq("entities/tank/basic/tank_basic_up_1.png", "entities/tank/basic/tank_basic_up_2.png"), tileSize, viewScale)
                val playerView = JFXTankView(playerImages, tileSize)
                Seq((player, playerView))
            case StringIndestructibleWall => LevelObstacle.IndestructibleWall(x, y).map(t => ((
                t,
                JFXObstacleView.create(JFXImageLoader.loadFromResources(t.imagesPath(0), tileSize, viewScale))
            )))