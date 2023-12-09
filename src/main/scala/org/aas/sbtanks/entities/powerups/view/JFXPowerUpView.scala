package org.aas.sbtanks.entities.powerups.view

import javafx.event.Event
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.util.Duration
import org.aas.sbtanks.common.view.JFXMoveableView
import org.aas.sbtanks.entities.powerups.view.scalaFx.JFXIntermittentVisibleNode



class JFXPowerUpView(image: Image) extends ImageView(image) with PowerUpView with JFXMoveableView[ImageView] with JFXIntermittentVisibleNode:
    
    override def show(): Unit = this.animate()

    override def destroy(): Unit = ???




object JFXPowerUpView:
    def apply(imagePath: String): JFXPowerUpView =
        new JFXPowerUpView(Image(imagePath))




object PowerUpViewTest extends JFXApp3:


    override def start(): Unit =
        val view =  JFXPowerUpView("entities/powerups/powerup_star.png")

        //view.animate()

        stage = new PrimaryStage:
            title = "Power-Up View"
            scene = new Scene:
                content = ImageView(view)



