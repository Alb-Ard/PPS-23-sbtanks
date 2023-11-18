package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerRemainingEnemiesView
import scalafx.scene.layout.FlowPane
import scalafx.scene.image.ImageView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import javafx.application.Platform

class JFXPlayerRemainingEnemiesView(interfaceScale: Double) extends FlowPane with PlayerRemainingEnemiesView:
    prefWrapLength = 16D * interfaceScale

    override def setEnemyCount(count: Int): Unit = Platform.runLater { () =>
        children.clear()
        for i <- 0 until count do children.add(createEnemyIcon())
    }

    override def enemyDefeated(): Unit = 
        children.headOption.foreach(c => children.removeAll(c))

    def createEnemyIcon() =
        ImageView(JFXImageLoader.loadFromResources("ui/enemy_icon.png", 8, interfaceScale))