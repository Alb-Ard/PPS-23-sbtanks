package org.aas.sbtanks.entities.obstacles.view.scalafx

import org.aas.sbtanks.entities.obstacles.view.ObstacleView
import org.aas.sbtanks.common.view.JFXMoveableView
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image

class JFXObstacleView(image: Image) extends ImageView(image)
    with ObstacleView
    with JFXMoveableView

object JFXObstacleView:
    def create(image: Image) =
        JFXObstacleView(image)

    def createAnimated(images: Seq[Image], animationSpeed: Double) =
        JFXAnimatedObstacleView(images, animationSpeed)