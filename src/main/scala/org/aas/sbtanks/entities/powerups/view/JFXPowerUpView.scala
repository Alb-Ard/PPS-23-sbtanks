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





object JFXPowerUpView:
    def apply(imagePath: String): JFXPowerUpView =
        new JFXPowerUpView(Image(imagePath))







