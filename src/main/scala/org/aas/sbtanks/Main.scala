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
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerTankController
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.player.PlayerTankBuilder
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

object Main extends JFXApp3 with scalafx.Includes:
    val inputController = JFXPlayerInputController()
    val viewScale = 4D

    override def start(): Unit = 
        val testTank = PlayerTankBuilder()
            .setPosition(0, 0)
            .build()
        val testTankImage1 = JFXImageLoader.loadFromResources("entities/tank/basic/tank_basic_up_1.png", viewScale)
        val testTankImage2 = JFXImageLoader.loadFromResources("entities/tank/basic/tank_basic_up_2.png", viewScale)
        val testTankView = JFXTankView(Seq(testTankImage1, testTankImage2), 4)
        val testTankController = JFXPlayerTankController(testTank, testTankView, viewScale * 16)

        val testWall = LevelObstacle.BrickWall(32, 32)
        val testWallView = ImageView(JFXImageLoader.loadFromResources(testWall.imagePath, viewScale))
        testWallView.x = testWall.positionX * viewScale
        testWallView.y = testWall.positionY * viewScale

        val testTrees = LevelObstacle.Trees(32, 48)
        val testTreesView = ImageView(JFXImageLoader.loadFromResources(testTrees.imagePath, viewScale))
        testTreesView.x = testTrees.positionX * viewScale
        testTreesView.y = testTrees.positionY * viewScale

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