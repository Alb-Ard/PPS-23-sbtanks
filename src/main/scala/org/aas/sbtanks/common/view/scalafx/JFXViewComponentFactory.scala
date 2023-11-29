package org.aas.sbtanks.common.view.scalafx

import scalafx.scene.control.Button
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image

object JFXViewComponentFactory:
    def createButton(size: (Int, Int), iconSize: (Int, Int), interfaceScale: Double, text: String) =
        val button = Button(text)
        button.prefWidth = size(0) * interfaceScale
        button.prefHeight = size(1) * interfaceScale
        button.styleClass.addAll("main-menu-text", "main-menu-button")
        val icon = ImageView(Image("ui/main_menu_selected_item_icon.png", iconSize(0) * interfaceScale, iconSize(1) * interfaceScale, true, false, false))
        button.graphic = icon
        icon.opacity = 0
        button.onMouseEntered = (e) => icon.opacity = 1
        button.onMouseExited = (e) => icon.opacity = 0
        button