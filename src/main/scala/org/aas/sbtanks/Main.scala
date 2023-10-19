package org.aas.sbtanks

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyEvent
import scalafx.scene.image.Image
import scalafx.animation.AnimationTimer

import org.aas.sbtanks.player.scalafx.JFXPlayerInputController
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.player.scalafx.JFXPlayerTankController
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour

object Main extends JFXApp3 with scalafx.Includes:
    val inputController = JFXPlayerInputController()
    val viewScale = 4D

    override def start(): Unit = 
        val testTank = new Object()
            with PositionBehaviour(0, 0)
            with ConstrainedMovementBehaviour 
            with SteppedMovementDirectionBehaviour(4 * viewScale)
            with CollisionBehaviour(16, 16, CollisionLayer.TanksLayer, Seq(CollisionLayer.BulletsLayer, CollisionLayer.WallsLayer))
        val testTankImage1 = Image("entities/tank/basic/tank_basic_up_1.png", 16 * viewScale, 16 * viewScale, true, false)
        val testTankImage2 = Image("entities/tank/basic/tank_basic_up_2.png", 16 * viewScale, 16 * viewScale, true, false)
        val testTankView = JFXTankView(Seq(testTankImage1, testTankImage2), 4)
        val testTankController = JFXPlayerTankController(testTank, testTankView, viewScale)

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