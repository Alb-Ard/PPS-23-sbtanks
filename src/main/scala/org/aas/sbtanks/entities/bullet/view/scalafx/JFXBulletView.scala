package org.aas.sbtanks.entities.bullet.view.scalafx

import javafx.animation.Animation
import org.aas.sbtanks.common.view.{DirectionableView, JFXDirectionableView, JFXMoveableView}
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.shape.Rectangle

class JFXBulletView(image: Image, animationSpeed: Double = 1) extends ImageView with JFXMoveableView with JFXDirectionableView:

    //ImageView.sfxImageView2jfx().setImage(JFXImageLoader.loadFromResources(1, 1))

