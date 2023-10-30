package org.aas.sbtanks.common.view.scalafx

import scalafx.animation.AnimationTimer
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView

trait JFXImageViewAnimator(images: Seq[Image], speed: Double, private var running: Boolean = false):
    this: ImageView =>

    private val animationTimer = AnimationTimer { now =>
        image = images(((now / 1000D / 1000D / 1000D) * speed % images.length).asInstanceOf[Int])
    }

    def isAnimationRunning = running

    def startAnimation() =
        running = true
        animationTimer.start()
        this

    def stopAnimation() =
        running = false
        animationTimer.stop()
        this


