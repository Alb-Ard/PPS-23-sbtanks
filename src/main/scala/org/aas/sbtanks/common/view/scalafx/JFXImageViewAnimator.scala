package org.aas.sbtanks.common.view.scalafx

import scalafx.animation.AnimationTimer
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.application.Platform

trait JFXImageViewAnimator(private var images: Seq[Image], speed: Double, private var running: Boolean = false):
    this: ImageView =>

    private val animationTimer = AnimationTimer { now =>
        image = images(((now / 1000D / 1000D / 1000D) * speed % images.length).asInstanceOf[Int])
    }

    def isAnimationRunning = running

    def startAnimation(): this.type =
        running = true
        animationTimer.start()
        this

    def stopAnimation(): this.type =
        running = false
        animationTimer.stop()
        this

    def setImages(images: Seq[Image]): this.type =
        this.images = images
        //Platform.runLater { image = images(0) }
        this