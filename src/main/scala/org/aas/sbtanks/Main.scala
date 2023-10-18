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

object Main extends JFXApp3 with scalafx.Includes:
    val inputController = JFXPlayerInputController()

    override def start(): Unit = 
        val testTank = new Object() with MovementBehaviour(0, 0) with SteppedMovementDirectionBehaviour(20)
        val testTankImage = Image("entities/tank/basic/tank_basic_up_1.png", 64, 64, true, false)
        val testTankView = JFXTankView(testTankImage)
        val testTankController = JFXPlayerTankController(testTank, testTankView)

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