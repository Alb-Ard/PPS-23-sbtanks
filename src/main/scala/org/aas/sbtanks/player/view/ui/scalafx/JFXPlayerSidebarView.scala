package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerSidebarView
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.Pane
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox

class JFXPlayerSidebarView(override val healthView: JFXPlayerHealthView, private val viewportHeight: Double, private val sidebarWidth: Double) extends Pane
    with PlayerSidebarView(healthView):

    children.add(new Rectangle:
        width = sidebarWidth
        height = viewportHeight
        fill.set(PlayerSidebarView.SIDEBAR_BACKGROUND_FILL))
    private val elementsContainer = VBox()
    children.add(elementsContainer)
    elementsContainer.padding = Insets(16D)
    elementsContainer.children.add(healthView)

object JFXPlayerSidebarView:
    val SIDEBAR_WIDTH = 128

    def create(interfaceScale: Double, viewportHeight: Double) =
        JFXPlayerSidebarView(JFXPlayerHealthView(interfaceScale), viewportHeight, SIDEBAR_WIDTH)