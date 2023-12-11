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


/**
 * A graphical representation of a power-up in a JavaFX application. This class extends JavaFX's ImageView and
 * includes traits for power-up functionality, moveable behavior, and intermittent visibility.
 *
 * @param image The image to be displayed for the power-up.
 */
class JFXPowerUpView(image: Image) extends ImageView(image) with PowerUpView
    with JFXMoveableView[ImageView]
    with JFXIntermittentVisibleNode:

    /**
     * Displays the power-up with an animation over a specified duration. This method simply delegate the work to IntermittentVisibleNode method
     *
     * @param duration The total duration of the animation in seconds.
     */
    override def show(duration: Double): Unit = this.animate(duration)





object JFXPowerUpView:
    def apply(imagePath: String): JFXPowerUpView =
        new JFXPowerUpView(Image(imagePath))







