package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerHealthView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

class JFXPlayerHealthView(interfaceScale: Double) extends Pane with PlayerHealthView:
    private val playerName = new Text("IP"):
        styleClass.add("sidebar-text")
    private val lifesCount = new Text("X"):
        x = 8D * interfaceScale
        y = 8D * interfaceScale
        styleClass.add("sidebar-text")
    private val tankIcon = ImageView(JFXImageLoader.loadFromResources("ui/player_lifes_icon.png", 8, interfaceScale))

    children.addAll(playerName, lifesCount, tankIcon)

    override def setRemainingHealth(amount: Int) =
        lifesCount.text.set(amount.toString())
        this

