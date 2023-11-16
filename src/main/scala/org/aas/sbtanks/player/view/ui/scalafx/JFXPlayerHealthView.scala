package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerHealthView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

class JFXPlayerHealthView(interfaceScale: Double) extends Pane with PlayerHealthView:
    private val label = new Text("X"):
        x = 8D * interfaceScale
        style = "font-family: 'Press Start 2P'; src: "
    private val tankIcon = ImageView(JFXImageLoader.loadFromResources("ui/player_lifes_icon.png", 8, interfaceScale))

    children.addAll(label, tankIcon)

    override def setRemainingHealth(amount: Int) =
        label.text.set(amount.toString())
        this

