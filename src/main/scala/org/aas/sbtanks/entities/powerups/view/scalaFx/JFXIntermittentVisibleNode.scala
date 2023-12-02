package org.aas.sbtanks.entities.powerups.view.scalaFx

import org.aas.sbtanks.entities.powerups.view.IntermittentVisible
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.beans.property.BooleanProperty
import javafx.scene.Node
import scalafx.scene.image.ImageView
import scalafx.util.Duration

trait JFXIntermittentVisibleNode extends IntermittentVisible:
    this: ImageView =>

    private val isVisibleOnScene: BooleanProperty = BooleanProperty(true)

    this.visibleProperty().bind(isVisibleOnScene)

    private val timeLine: Timeline = new Timeline:
        keyFrames = Seq(
            KeyFrame(Duration(500), onFinished = _ => isVisibleOnScene.value = !isVisibleOnScene.value)
        )
        cycleCount = 10
        onFinished = _ => isVisibleOnScene.value = false

    override def animate(): Unit = this.timeLine.play()
