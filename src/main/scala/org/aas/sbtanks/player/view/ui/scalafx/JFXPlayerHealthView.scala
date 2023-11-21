package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerHealthView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

/**
  * UI Control that shows the remaining player lifes
  *
  * @param interfaceScale The interface scaling factor
  */
class JFXPlayerHealthView(interfaceScale: Double) extends Pane with PlayerHealthView:
    private val ICON_SIZE = 8

    private val playerName = new Text("IP"):
        styleClass.add("sidebar-text")
    private val lifesCount = new Text("X"):
        x = ICON_SIZE * interfaceScale
        y = ICON_SIZE * interfaceScale
        styleClass.add("sidebar-text")
    private val tankIcon = ImageView(JFXImageLoader.loadFromResources("ui/player_lifes_icon.png", ICON_SIZE, interfaceScale))

    children.addAll(playerName, lifesCount, tankIcon)

    /**
      * @inheritDoc
      */
    override def setRemainingHealth(amount: Int) =
        lifesCount.text.set(amount.toString())
        this

