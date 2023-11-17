package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerRemainingEnemiesView
import scalafx.scene.layout.FlowPane
import scalafx.scene.image.ImageView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import javafx.application.Platform

class JFXPlayerRemainingEnemiesView(interfaceScale: Double) extends FlowPane with PlayerRemainingEnemiesView:
    prefWrapLength = 16D * interfaceScale

    override def setEnemyCount(count: Int): Unit = Platform.runLater { () =>
        (count - children.length) match
            case p if p > 0 =>
                for i <- 0 until p do children.add(createEnemyIcon())
            case n if n < 0 =>
                for i <- 0 until n do children.remove(children.length - 1)        
    }

    def createEnemyIcon() =
        ImageView(JFXImageLoader.loadFromResources("ui/enemy_icon.png", 8, interfaceScale))