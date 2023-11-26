package org.aas.sbtanks.enemies.view

import org.aas.sbtanks.common.view.MoveableView

trait EnemySpawnView extends MoveableView:

    def initSpawnAnimation(): this.type

