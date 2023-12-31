package org.aas.sbtanks.common.view.scalafx

import scalafx.scene.control.{Button, TextField}
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.Includes
import scalafx.geometry.Pos.Center
import scalafx.scene.text.Text
import scalafx.scene.control.Slider
import scalafx.scene.paint.Color

object JFXViewComponentFactory extends Includes:
    def createButton(size: (Int, Int), iconSize: (Int, Int), interfaceScale: Double, text: String, classes: Seq[String]) =
        val button = Button(text)
        button.prefWidth = size(0) * interfaceScale
        button.prefHeight = size(1) * interfaceScale
        button.styleClass.addAll(classes)
        val icon = ImageView(Image("ui/main_menu_selected_item_icon.png", iconSize(0) * interfaceScale, iconSize(1) * interfaceScale, true, false, false))
        button.graphic = icon
        icon.opacity <== when(button.hover) choose 1 otherwise 0
        button
    
    def createText(text: String, classes: Seq[String]) =
        new Text(text):
            fill = Color.White
            styleClass.addAll(classes)

    def createTextField(classes: Seq[String]) =
        new TextField():
            alignment = Center
            styleClass.addAll(classes)
    
    def createSlider(min: Double, max: Double, value: Double, classes: Seq[String]) = 
        new Slider(min, max, value):
            styleClass.addAll(classes)