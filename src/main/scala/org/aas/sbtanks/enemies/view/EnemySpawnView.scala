package org.aas.sbtanks.enemies.view

import org.aas.sbtanks.common.view.MoveableView

/**
 * A trait view to display an animation, it extends MovableView to provide a position in the world for it
 */
trait EnemySpawnView extends MoveableView:

    /**
     * Initializes the enemy spawn animation by starting the animation.
     *
     * @return The current instance mixed in extending traits.
     */
    def initSpawnAnimation(): this.type

