package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerLevelNumberView
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text
import scalafx.scene.image.ImageView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

/**
  * UI Control that shows the number of the level tha player is currently playing
  * 
  * @param interfaceScale The interface scaling factor
  */
class JFXPlayerLevelNumberView(interfaceScale: Double) extends Pane with PlayerLevelNumberView:
    private val ICON_SIZE = 16

    private val numberText = new Text("X"):
        styleClass.add("sidebar-text")
        x = ICON_SIZE / 2D * interfaceScale
        y = ICON_SIZE * 1.5D * interfaceScale
    private val iconImage = new ImageView(JFXImageLoader.loadFromResources("ui/level_number_icon.png", ICON_SIZE, interfaceScale))
    children.addAll(numberText, iconImage)

    /**
      * @inheritDoc
      */
    override def setLevelNumber(number: Int) = 
        numberText.text = number.toString()
        this