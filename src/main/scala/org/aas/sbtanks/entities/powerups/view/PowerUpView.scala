package org.aas.sbtanks.entities.powerups.view

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.BooleanProperty
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.util.Duration



object PowerUpView extends JFXApp3:

    override def start(): Unit =

        val powerUpImage = new Image("entities/powerups/powerup_star.png")
        val powerUpImageView = new ImageView(powerUpImage)

        val isVisible = BooleanProperty(true)

        powerUpImageView.visibleProperty().bind(isVisible)


        val timeline = new Timeline {
            keyFrames = Seq(
                KeyFrame(Duration(500), onFinished = _ => isVisible.value = !isVisible.value)
            )
            cycleCount = 10
            onFinished = _ => isVisible.value = false

        }

        timeline.play()

        stage = new PrimaryStage:
            title = "Power-Up View"
            scene = new Scene:
                content = powerUpImageView
