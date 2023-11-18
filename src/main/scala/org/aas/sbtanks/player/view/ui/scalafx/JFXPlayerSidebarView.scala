package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerSidebarView
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.Pane
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox

class JFXPlayerSidebarView(override val healthView: JFXPlayerHealthView,
                           override val remainingEnemiesView: JFXPlayerRemainingEnemiesView,
                           private val interfaceScale: Double,
                           private val viewportHeight: Double,
                           private val sidebarWidth: Double)
    extends Pane
    with PlayerSidebarView(healthView, remainingEnemiesView):

    children.add(new Rectangle:
        width = sidebarWidth
        height = viewportHeight
        fill.set(PlayerSidebarView.SIDEBAR_BACKGROUND_FILL))
    private val elementsContainer = VBox()
    children.add(elementsContainer)
    elementsContainer.padding = Insets(16D)
    elementsContainer.children.addAll(remainingEnemiesView, healthView)
    healthView.translateY = 16D * interfaceScale

object JFXPlayerSidebarView:
    val SIDEBAR_WIDTH = 128

    def create(interfaceScale: Double, viewportHeight: Double) =
        JFXPlayerSidebarView(JFXPlayerHealthView(interfaceScale), JFXPlayerRemainingEnemiesView(interfaceScale), interfaceScale, viewportHeight, SIDEBAR_WIDTH)