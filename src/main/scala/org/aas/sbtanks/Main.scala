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

object Main extends JFXApp3 with scalafx.Includes:
    val inputController = JFXPlayerInputController()

    override def start(): Unit = 
        val testTank = new Object() with MovementBehaviour(0, 0) with SteppedMovementDirectionBehaviour(5)
        val testTankImage = Image("entities/tank/basic/tank_basic_up_1.png")
        val testTankView = JFXTankView(testTankImage)

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

        stage.addEventHandler(KeyEvent.KeyPressed, inputController.handleKeyPressEvent)
        //stage.addEventHandler(KeyEvent.KeyReleased, inputController.handleKeyReleasedEvent)
        inputController.moved += testTank.moveTowards
        testTank.stepMoved += testTank.moveRelative

        var lastTimeNanos = System.nanoTime().doubleValue
        val updateTimer = AnimationTimer(deltaMillis => {
            val currentTimeNanos = System.nanoTime().doubleValue
            val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
            testTank.step(deltaTime)
            lastTimeNanos = currentTimeNanos
        })
        updateTimer.start()