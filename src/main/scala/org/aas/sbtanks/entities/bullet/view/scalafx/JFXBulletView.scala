package org.aas.sbtanks.entities.bullet.view.scalafx

import javafx.animation.Animation
import org.aas.sbtanks.common.view.{DirectionableView, JFXDirectionableView, JFXMoveableView}
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.shape.Rectangle

class JFXBulletView(private val image: Image, animationSpeed: Double = 1) extends JFXDirectionableView with JFXMoveableView:

    override def look(rotation: Double): Unit = super.look(rotation)

    override def move(x: Double, y: Double): Unit = super.move(x, y)

    //ImageView.sfxImageView2jfx().setImage(JFXImageLoader.loadFromResources(1, 1))

