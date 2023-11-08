package org.aas.sbtanks.entities.bullet.view.scalafx

import org.aas.sbtanks.common.view.{JFXDirectionableView, JFXMoveableView}
import org.aas.sbtanks.entities.bullet.view.BulletView
import scalafx.Includes
import scalafx.scene.image.{Image, ImageView}

class JFXBulletView(private val bulletImage: Seq[Image], animationSpeed: Double = 1) extends ImageView
    with BulletView
    with Includes
    /*
    with BulletController(bullet: Bullet with PositionBehaviour
                            with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour)
     */
    with JFXDirectionableView
    with JFXMoveableView[ImageView]

