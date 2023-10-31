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
                    x = 10
                    y = 10
                    width = 1260
                    height = 700
                    fill = Color.LIGHTGREY

        given EntityRepositoryContext[Stage] = EntityRepositoryContext(stage)
        val entityRepository = new JFXEntityMvRepositoryContainer()
                with JFXEntityControllerRepository
                with JFXEntityViewAutoManager
                with EntityRepositoryContextAware

        entityRepository.registerControllerFactory(m => m.isInstanceOf[PlayerTank], JFXPlayerTankController.factory(tankUnitMoveSpeed, viewScale * tileSize))
                .registerControllerFactory(m => m.isInstanceOf[LevelObstacle], LevelObstacleController.factory[Stage](viewScale * tileSize))

        val testTank = PlayerTankBuilder()
                .setPosition(0, 0)
                .setCollisionSize(1D - tankUnitMoveSpeed, 1D - tankUnitMoveSpeed)
                .build()
        val testTankImages = JFXImageLoader.loadFromResources(Seq("entities/tank/basic/tank_basic_up_1.png", "entities/tank/basic/tank_basic_up_2.png"), tileSize, viewScale)
        val testTankView = JFXTankView(testTankImages, tileSize)
        entityRepository.addModelView(testTank, Option(testTankView))

        val testWalls = LevelObstacle.BrickWall(2, 2)
        val testWallViews = testWalls.map(w =>
                val view = JFXObstacleView.create(JFXImageLoader.loadFromResources(w.imagesPath(0), tileSize / 4, viewScale))
                entityRepository.addModelView(w, Option(view))
                view
            )

        val testTrees = LevelObstacle.Trees(2, 3)(0)
        val testTreesView = JFXObstacleView.create(JFXImageLoader.loadFromResources(testTrees.imagesPath(0), tileSize, viewScale))
        entityRepository.addModelView(testTrees, Option(testTreesView))
        
        val testWater = LevelObstacle.Water(2, 4)(0)
        val testWaterView = JFXObstacleView.createAnimated(JFXImageLoader.loadFromResources(testWater.imagesPath, tileSize, viewScale), 2.0)
            .startAnimation()
        entityRepository.addModelView(testWater, Option(testWaterView))

        var lastTimeNanos = System.nanoTime().doubleValue
        val updateTimer = AnimationTimer(_ => {
            val currentTimeNanos = System.nanoTime().doubleValue
            val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
            entityRepository.step(deltaTime)
            lastTimeNanos = currentTimeNanos
        })
        updateTimer.start()