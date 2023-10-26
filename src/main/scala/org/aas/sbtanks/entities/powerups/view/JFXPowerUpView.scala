package org.aas.sbtanks.entities.powerups.view

import javafx.event.Event
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.util.Duration
import javafx.scene.image as jfxsi







class JFXPowerUpView(private val image: Image) extends jfxsi.ImageView(image) with IntermittentVisible[javafx.scene.Node]:

    private val isVisibleOnScene: BooleanProperty = BooleanProperty(true)

    this.visibleProperty().bind(isVisibleOnScene)

    private val timeLine: Timeline = new Timeline:
        keyFrames = Seq(
            KeyFrame(Duration(500), onFinished = _ => isVisibleOnScene.value = !isVisibleOnScene.value)
        )
        cycleCount = 10
        onFinished = _ => isVisibleOnScene.value = false


    override def activate(): Unit =
        timeLine.play()




object JFXPowerUpView:
    def apply(imagePath: String): JFXPowerUpView =
        new JFXPowerUpView(Image(imagePath))




object PowerUpViewTest extends JFXApp3:


    override def start(): Unit =
        val view =  JFXPowerUpView("entities/powerups/powerup_star.png")

        view.activate()

        stage = new PrimaryStage:
            title = "Power-Up View"
            scene = new Scene:
                content = ImageView(view)



