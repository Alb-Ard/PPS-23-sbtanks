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
import org.aas.sbtanks.enemies.controller.{EnemyController, EnemySpawnController, EnemyTankBuilder, EnemyTankGenerator}
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
import org.aas.sbtanks.entities.repository.EntityControllerReplacer
import org.aas.sbtanks.entities.repository.EntityColliderAutoManager
import org.aas.sbtanks.player.view.ui.scalafx.JFXPlayerSidebarView
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.Pane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Region
import scalafx.geometry.Pos
import org.aas.sbtanks.player.controller.PlayerUiViewController
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank

import scala.collection.immutable.Queue

object Main extends JFXApp3 with scalafx.Includes:
    val viewScale = 4D
    val tileSize = 16D
    val tankUnitMoveSpeed = 1D / tileSize
    val windowSize = (1280, 720)
    val interfaceScale = 4D

    val pixelSize = 1D / tileSize

    override def start(): Unit = 
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = windowSize(0)
            height = windowSize(1)
            scene = new Scene:
                fill = Color.BLACK
                stylesheets.add(getClass().getResource("/ui/style.css").toExternalForm())

        val entityViewContainer = Pane()
        val scenePane = BorderPane(center = null, right = null, top = null, bottom = null, left = null)
        BorderPane.setAlignment(entityViewContainer, Pos.CENTER)
        scenePane.center.set(entityViewContainer)

        val playerSidebar = JFXPlayerSidebarView.create(interfaceScale, windowSize(1))
        scenePane.right.set(playerSidebar)

        stage.scene.value.content.add(scenePane)

        given EntityRepositoryContext[Stage, Pane] = EntityRepositoryContext(stage, entityViewContainer)
        val entityRepository = new JFXEntityMvRepositoryContainer()
                with JFXEntityControllerRepository
                with JFXEntityViewAutoManager
                with EntityControllerReplacer[AnyRef, Node, EntityRepositoryContext[Stage, Pane]]
                with DestroyableEntityAutoManager[AnyRef, Node]
                with EntityRepositoryTagger[AnyRef, Node, Int]
                with EntityColliderAutoManager[AnyRef, Node]
                with EntityRepositoryContextAware

        entityRepository.registerControllerFactory(m => m.isInstanceOf[PlayerTank], JFXPlayerTankController.factory(tankUnitMoveSpeed, viewScale * tileSize, (bulletModel, bulletView) => entityRepository.addModelView(bulletModel, Option(bulletView))))
                .registerControllerFactory(m => m.isInstanceOf[LevelObstacle], LevelObstacleController.factory(viewScale * tileSize))
                .registerControllerFactory(m => m.isInstanceOf[Tank] && !m.isInstanceOf[PlayerTank] && m.asInstanceOf[Tank].tankData.health < 10, EnemySpawnController.factory(viewScale * tileSize, entityRepository))
                .registerControllerReplacer(m => m.isInstanceOf[Tank] && !m.isInstanceOf[PlayerTank] && m.asInstanceOf[Tank].tankData.health > 10, EnemyController.factory(viewScale * tileSize))

        val playerUiViewController = PlayerUiViewController[AnyRef, Node](entityRepository, playerSidebar);
        entityRepository.addController(playerUiViewController)


        val tank = EnemyTankBuilder()
            .setPosition(1.0, 1.0)
            .setCollisionSize(x = 1D - pixelSize, y = 1D - pixelSize)
            .build()

        val g = new EnemyTankGenerator(entityRepository, tank)

        //entityRepository.addController(g)

        g.loadTank()




        // ** TEST **
        val level1 = ("UUUUUUU" +
                      "U-TTT-U" +
                      "U-SwS-U" +
                      "U--P--U" +
                      "U-WWW-U" +
                      "U-WBW-U" +
                      "UUUUUUU", 7, 10)
        val level2 = ("UUUUUUU" +
                      "U-----U" +
                      "UTSWSTU" +
                      "U-P---U" +
                      "U-WWW-U" +
                      "U-WBW-U" +
                      "UUUUUUU", 7, 20)

        // **********
        val levelFactory = JFXLevelFactory(tileSize, viewScale, 1)
        val levelSequencer = LevelSequencer[AnyRef, Node](Seq(level1, level2), levelFactory, entityRepository)
        levelSequencer.levelChanged += { (_, enemyCount) => playerUiViewController.setEnemyCount(enemyCount) }
        levelSequencer.start()




        var lastTimeNanos = System.nanoTime().doubleValue
        // ** TEST **
        var testTime = 2D
        // **********
        val updateTimer = AnimationTimer(_ => {
            val currentTimeNanos = System.nanoTime().doubleValue
            val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
            entityRepository.step(deltaTime)
            lastTimeNanos = currentTimeNanos
            // ** TEST **
            //if testTime > 0 && testTime - deltaTime < 0 then
            //    levelSequencer.completeLevel()
            //testTime -= deltaTime
            // **********
        })
        updateTimer.start()

        val ints = List(1, 2, 3, 4, 5)

        for i <- ints do println(i)