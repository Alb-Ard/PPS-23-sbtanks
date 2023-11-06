package org.aas.sbtanks.entities.bullet.view.scalafx

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.common.view.{DirectionableView, JFXDirectionableView, JFXMoveableView}
import org.aas.sbtanks.entities.bullet.view.BulletView
import scalafx.Includes
import scalafx.delegate.PositionDelegate
import scalafx.scene.{Node, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.shape.Rectangle
import org.aas.sbtanks.entities.bullet.controller.BulletController
import org.aas.sbtanks.entities.bullet.Bullet

class JFXBulletView(private val bulletImage: Image, animationSpeed: Double = 1) extends ImageView
    with BulletView
    with Includes
    /*
    with BulletController(bullet: Bullet with PositionBehaviour
                            with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour)
     */
    with JFXDirectionableView
    with JFXMoveableView[ImageView]:


    override def look(rotation: Double): Unit = bulletImage
    override def move(x: Double, y: Double): Unit = super.move(x, y)

    //ImageView.sfxImageView2jfx().setImage(JFXImageLoader.loadFromResources(1, 1))

