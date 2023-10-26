package org.aas.sbtanks.entities.powerups.view

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.util.Duration

import javafx.scene.{image => jfxsi}




class PowerUpView(private val image: Image) extends jfxsi.ImageView(image):

    private val isVisibleOnScene: BooleanProperty = BooleanProperty(true)

    this.visibleProperty().bind(isVisibleOnScene)


    private val timeLine: Timeline = new Timeline:
        keyFrames = Seq(
            KeyFrame(Duration(500), onFinished = _ => isVisibleOnScene.value = !isVisibleOnScene.value)
        )
        cycleCount = 10
        onFinished = _ => isVisibleOnScene.value = false

    def startAnimation(): Unit =
        timeLine.play()

object PowerUpView:
    def apply(imagePath: String): PowerUpView =
        new PowerUpView(Image(imagePath))




object PowerUpViewTest extends JFXApp3:


    override def start(): Unit =
        val view =  PowerUpView("entities/powerups/powerup_star.png")

        view.startAnimation()

        stage = new PrimaryStage:
            title = "Power-Up View"
            scene = new Scene:
                content = ImageView(view)




/*
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
*/