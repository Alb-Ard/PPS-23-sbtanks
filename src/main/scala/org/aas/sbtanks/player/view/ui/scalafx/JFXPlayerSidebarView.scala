package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerSidebarView
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.Pane
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox
import scalafx.beans.property.IntegerProperty

class JFXPlayerSidebarView(override val healthView: JFXPlayerHealthView,
                           override val remainingEnemiesView: JFXPlayerRemainingEnemiesView,
                           override val levelNumberView: JFXPlayerLevelNumberView,
                           private val interfaceScale: Double,
                           private val viewportHeight: IntegerProperty,
                           private val sidebarWidth: Double)
    extends Pane
    with PlayerSidebarView(healthView, remainingEnemiesView, levelNumberView):

    children.add(new Rectangle:
        width = sidebarWidth
        height <== viewportHeight
        fill.set(PlayerSidebarView.SIDEBAR_BACKGROUND_FILL))
    private val elementsContainer = VBox()
    children.add(elementsContainer)
    elementsContainer.padding = Insets(16D)
    elementsContainer.children.addAll(remainingEnemiesView, healthView, levelNumberView)
    healthView.translateY = 16D * interfaceScale
    levelNumberView.translateY = 16D * interfaceScale

object JFXPlayerSidebarView:
    val SIDEBAR_WIDTH = 128

    def create(interfaceScale: Double, viewportHeight: IntegerProperty) =
        JFXPlayerSidebarView(JFXPlayerHealthView(interfaceScale), JFXPlayerRemainingEnemiesView(interfaceScale), JFXPlayerLevelNumberView(interfaceScale), interfaceScale, viewportHeight, SIDEBAR_WIDTH)