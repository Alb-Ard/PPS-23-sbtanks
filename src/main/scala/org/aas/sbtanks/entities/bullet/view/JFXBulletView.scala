package org.aas.sbtanks.entities.bullet.view

import javafx.animation.Animation
import org.aas.sbtanks.common.view.MoveableView
import org.aas.sbtanks.common.view.DirectionableView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import scalafx.scene.image.ImageView
import scalafx.scene.Scene
import scalafx.scene.shape.Rectangle
import scalafx.scene.Node
import scalafx.scene.image.Image

class JFXBulletView(image: Image, animationSpeed: Double = 1) extends ImageView with MovableView with DirectionableView with JFXImageLoader:

    ImageView.sfxImageView2jfx().setImage(JFXImageLoader.loadFromResources(1, 1))

