package org.aas.sbtanks.enemies.view.scalafx

import org.aas.sbtanks.common.view.JFXMoveableView
import org.aas.sbtanks.common.view.scalafx.JFXImageViewAnimator
import org.aas.sbtanks.enemies.view.EnemySpawnView
import scalafx.scene.image.{Image, ImageView}
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

/**
 * A JavaFX view for enemy spawn animation, extending ImageView and implementing related traits
 * @param images sequence of images to supply to the animation frames
 * @param animationSpeed the speed at which the animation should play (default is 1)
 */
class JFXEnemySpawnView(private val images: Seq[Image], animationSpeed: Double = 1)
    extends ImageView(images.head)
    with EnemySpawnView
    with JFXImageViewAnimator(images, animationSpeed, false)
    with JFXMoveableView[ImageView]:

    /**
     * Initializes the enemy spawn animation by starting the animation.
     *
     * @return The current instance of JFXEnemySpawnView.
     */
    override def initSpawnAnimation(): JFXEnemySpawnView.this.type =
        this.startAnimation()



object JFXEnemySpawnView:
    def apply(tileSize: Double, viewScale: Double, animationSpeed: Double): JFXEnemySpawnView =
        new JFXEnemySpawnView(JFXImageLoader.loadFromResources(Seq("respawn_1.png", "respawn_2.png", "respawn_3.png", "respawn_4.png").map("effects/"+ _), tileSize, viewScale), animationSpeed)

