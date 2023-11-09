package org.aas.sbtanks.entities.bullet.view.scalafx

import org.aas.sbtanks.common.view.{JFXDirectionableView, JFXMoveableView}
import org.aas.sbtanks.entities.bullet.view.BulletView
import scalafx.scene.image.{Image, ImageView}

class JFXBulletView(private val bulletImage: Image) extends ImageView(bulletImage)
    with BulletView
    with JFXDirectionableView
    with JFXMoveableView[ImageView]



