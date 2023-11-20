package org.aas.sbtanks.player.view.ui

import _root_.scalafx.scene.paint.Color

trait PlayerSidebarView(val healthView: PlayerHealthView, val remainingEnemiesView: PlayerRemainingEnemiesView)

object PlayerSidebarView:
    val SIDEBAR_BACKGROUND_FILL = Color.rgb(63, 63, 63)