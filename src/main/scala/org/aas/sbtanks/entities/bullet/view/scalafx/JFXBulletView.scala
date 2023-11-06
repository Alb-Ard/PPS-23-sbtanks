package org.aas.sbtanks.entities.bullet.view.scalafx

import org.aas.sbtanks.common.view.{DirectionableView, JFXDirectionableView, JFXMoveableView}
import org.aas.sbtanks.entities.bullet.view.BulletView
import scalafx.Includes
import scalafx.delegate.PositionDelegate
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.shape.Rectangle
import org.aas.sbtanks.entities.bullet.controller.BulletController

class JFXBulletView(private val bulletImage: Seq[Image], animationSpeed: Double = 1) extends ImageView
    with BulletView
    with Includes
    with BulletController(private val bullet: Bullet)
    with JFXDirectionableView
    with JFXMoveableView[ImageView]:
    override def look(rotation: Double): Unit = setImage(bulletImage)
    override def move(x: Double, y: Double): Unit = super.move(x, y)

    //ImageView.sfxImageView2jfx().setImage(JFXImageLoader.loadFromResources(1, 1))

