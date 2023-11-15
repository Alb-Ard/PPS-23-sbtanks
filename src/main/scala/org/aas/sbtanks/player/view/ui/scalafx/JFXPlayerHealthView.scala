package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerHealthView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

import scalafx.scene.control.Label
import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane

class JFXPlayerHealthView(interfaceScale: Double) extends Pane with PlayerHealthView:
    private val label = Label("X")
    private val tankIcon = ImageView(JFXImageLoader.loadFromResources("ui/player_lifes_icon.png", 8, interfaceScale))

    children.addAll(label, tankIcon)

    override def setRemainingHealth(amount: Int) =
        label.text.set(amount.toString())

