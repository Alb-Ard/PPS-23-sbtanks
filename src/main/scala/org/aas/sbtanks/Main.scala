package org.aas.sbtanks

import scalafx.application.JFXApp3
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

object Main extends JFXApp3 with scalafx.Includes:
    val inputController = JFXPlayerInputController()
    val viewScale = 4D
    val tileSize = 16D
    val tankUnitMoveSpeed = 1 / tileSize

    override def start(): Unit = 
        val testTank = PlayerTankBuilder()
            .setPosition(0, 0)
            .setCollisionSize(1 - tankUnitMoveSpeed, 1 - tankUnitMoveSpeed)
            .build()
        val testTankImages = JFXImageLoader.loadFromResources(Seq("entities/tank/basic/tank_basic_up_1.png", "entities/tank/basic/tank_basic_up_2.png"), viewScale)
        val testTankView = JFXTankView(testTankImages, tileSize)
        val testTankController = JFXPlayerTankController(testTank, tankUnitMoveSpeed, testTankView, viewScale * tileSize)

        val testWall = LevelObstacle.BrickWall(2, 2)
        val testWallView = ImageView(JFXImageLoader.loadFromResources(testWall.imagesPath(0), viewScale))
        testWallView.x = testWall.positionX * tileSize * viewScale
        testWallView.y = testWall.positionY * tileSize * viewScale

        val testTrees = LevelObstacle.Trees(2, 3)
        val testTreesView = ImageView(JFXImageLoader.loadFromResources(testTrees.imagesPath(0), viewScale))
        testTreesView.x = testTrees.positionX * tileSize * viewScale
        testTreesView.y = testTrees.positionY * tileSize * viewScale
        
        val testWater = LevelObstacle.Water(2, 4)
        val testWaterImages = JFXImageLoader.loadFromResources(testWater.imagesPath, viewScale)
        val testWaterView = new ImageView(testWaterImages(0))
            with JFXImageViewAnimator(testWaterImages, 2)
        testWaterView.startAnimation()
        testWaterView.x = testWater.positionX * tileSize * viewScale
        testWaterView.y = testWater.positionY * tileSize * viewScale

        stage = new JFXApp3.PrimaryStage {
            title = "sbTanks"
            width = 1280
            height = 720
            scene = new Scene {
                content = new Rectangle {
                    x = 10
                    y = 10
                    width = 1260
                    height = 700
                    fill = Color.LIGHTGREY
                }
                content.add(testTankView)
                content.add(testWallView)
                content.add(testTreesView)
                content.add(testWaterView)
            }
        }

        testTankController.registerSceneEventHandlers(stage)
        
        var lastTimeNanos = System.nanoTime().doubleValue
        val updateTimer = AnimationTimer(deltaMillis => {
            val currentTimeNanos = System.nanoTime().doubleValue
            val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
            testTankController.step(deltaTime)
            lastTimeNanos = currentTimeNanos
        })
        updateTimer.start()