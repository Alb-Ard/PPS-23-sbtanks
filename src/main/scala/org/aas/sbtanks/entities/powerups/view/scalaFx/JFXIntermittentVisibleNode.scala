package org.aas.sbtanks.entities.powerups.view.scalaFx

import org.aas.sbtanks.entities.powerups.view.IntermittentVisible
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.beans.property.BooleanProperty
import javafx.scene.Node
import scalafx.scene.image.ImageView
import scalafx.util.Duration

/**
 * A trait representing an intermittently visible JavaFX node. This trait extends the `IntermittentVisible` trait
 * and is specifically designed to be mixed into JavaFX ImageView instances.
 *
 * @tparam ImageView self-type reference to ensure that this trait can only be mixed into constructs
 *                                  that are also subtypes of ImageView.
 */
trait JFXIntermittentVisibleNode extends IntermittentVisible:
    this: ImageView =>

    private val cycles = 10

    private val secondsToMillis = 1000D

    private val isVisibleOnScene: BooleanProperty = BooleanProperty(true)

    this.visibleProperty().bind(isVisibleOnScene)

    private val timeLine = (d: Double) => new Timeline:
        keyFrames = Seq(
            KeyFrame(Duration((d / cycles) * secondsToMillis), onFinished = _ => isVisibleOnScene.value = !isVisibleOnScene.value)
        )
        cycleCount = cycles
        onFinished = _ => isVisibleOnScene.value = false

    /**
     * Animates the visibility of the node by creating and playing a timeline animation.
     *
     * @param duration The total duration of the animation in seconds.
     */
    override def animate(duration: Double): Unit =
        this.timeLine(duration).play()

