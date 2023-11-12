package org.aas.sbtanks.entities.bullet.view.scalafx

import org.aas.sbtanks.Main.stage
import org.aas.sbtanks.common.view.{JFXDirectionableView, JFXMoveableView}
import org.aas.sbtanks.entities.bullet.view.BulletView
import scalafx.application.JFXApp3
import scalafx.scene.image.{Image, ImageView}
import scalafx.application.JFXApp3
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyEvent
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.animation.AnimationTimer
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

class JFXBulletView(private val bulletImage: Image) extends ImageView(bulletImage)
    with BulletView
    with JFXDirectionableView
    with JFXMoveableView[ImageView]


object testJFXBulletView extends JFXApp3 with scalafx.Includes:
    val viewScale = 4D
    val tileSize = 16D
    val bulletView = new JFXBulletView(JFXImageLoader.loadFromResources("entities/bullet/bulletSprite.png", 16, 1280 * 720))

    override def start(): Unit =
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = 1280
            height = 720
            scene = new Scene:
                content = bulletView
                fill = Color.Black










