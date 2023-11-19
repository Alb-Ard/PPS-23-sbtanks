package org.aas.sbtanks.enemies.view.scalafx

import org.aas.sbtanks.common.view.scalafx.JFXImageViewAnimator
import org.aas.sbtanks.enemies.view.EnemySpawnView
import scalafx.scene.image.{Image, ImageView}

class JFXEnemySpawnView(private val images: Seq[Image], animationSpeed: Double = 1)
    extends ImageView(images.head)
    with EnemySpawnView
    with JFXImageViewAnimator(Seq.empty, animationSpeed, true):

    override def initSpawnAnimation(): JFXEnemySpawnView.this.type =
        this.setImages(images)

