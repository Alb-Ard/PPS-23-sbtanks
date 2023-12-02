package org.aas.sbtanks.entities.obstacles.view.scalafx

import org.aas.sbtanks.common.view.JFXMoveableView
import scalafx.scene.image.Image
import org.aas.sbtanks.common.view.scalafx.JFXImageViewAnimator

class JFXAnimatedObstacleView(images: Seq[Image], animationSpeed: Double) extends JFXObstacleView(images.applyOrElse(0, _ => null))
    with JFXImageViewAnimator(images, animationSpeed)