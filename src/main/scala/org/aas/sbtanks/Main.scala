package org.aas.sbtanks

import scalafx.application.JFXApp3
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyEvent
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.animation.AnimationTimer

import org.aas.sbtanks.player.controller.scalafx.JFXPlayerInputController
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerTankController
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.player.PlayerTankBuilder
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import org.aas.sbtanks.common.view.scalafx.JFXImageViewAnimator
import org.aas.sbtanks.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityControllerRepository
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityViewAutoManager
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import org.aas.sbtanks.player.PlayerTankData
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.obstacles.LevelObstacleController
import org.aas.sbtanks.level.scalafx.JFXLevelFactory
import org.aas.sbtanks.entities.repository.DestroyableEntityAutoManager
import scalafx.scene.Node
import org.aas.sbtanks.entities.repository.EntityRepositoryTagger

object Main extends JFXApp3 with scalafx.Includes:
    val viewScale = 4D
    val tileSize = 16D
    val tankUnitMoveSpeed = 1D / tileSize

    override def start(): Unit = 
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = 1280
            height = 720
            scene = new Scene:
                content = new Rectangle:
                    x = 0
                    y = 0
                    width = 1280
                    height = 720
                    fill = Color.BLACK

        given EntityRepositoryContext[Stage] = EntityRepositoryContext(stage)
        val entityRepository = new JFXEntityMvRepositoryContainer()
                with JFXEntityControllerRepository
                with JFXEntityViewAutoManager
                with DestroyableEntityAutoManager[AnyRef, Node]
                with EntityRepositoryTagger[AnyRef, Node, Int]
                with EntityRepositoryContextAware

        entityRepository.registerControllerFactory(m => m.isInstanceOf[PlayerTank], JFXPlayerTankController.factory(tankUnitMoveSpeed, viewScale * tileSize))
                .registerControllerFactory(m => m.isInstanceOf[LevelObstacle], LevelObstacleController.factory[Stage](viewScale * tileSize))

        val levelFactory = JFXLevelFactory(tileSize, viewScale, 1)
        levelFactory.createFromString("UUUUUUU" +
                                      "U-----U" +
                                      "U-SwS-U" +
                                      "U--P--U" +
                                      "U-WWW-U" +
                                      "U-WBW-U" +
                                      "UUUUUUU", 7, entityRepository)

        var lastTimeNanos = System.nanoTime().doubleValue
        val updateTimer = AnimationTimer(_ => {
            val currentTimeNanos = System.nanoTime().doubleValue
            val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
            entityRepository.step(deltaTime)
            lastTimeNanos = currentTimeNanos
        })
        updateTimer.start()