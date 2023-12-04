package org.aas.sbtanks.player.view.ui.scalafx

import org.aas.sbtanks.player.view.ui.PlayerRemainingEnemiesView
import scalafx.scene.layout.FlowPane
import scalafx.scene.image.ImageView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import javafx.application.Platform

/**
  * UI Control that shows the remaining enemies to defeat in a level using icons
  *
  * @param interfaceScale The UI scaling factor
  */
class JFXPlayerRemainingEnemiesView(interfaceScale: Double) extends FlowPane with PlayerRemainingEnemiesView:
    private val ICON_SIZE = 8
    private val ROW_ICON_COUNT = 2
    private val MIN_ROW_COUNT = 10

    prefWrapLength = ROW_ICON_COUNT * ICON_SIZE * interfaceScale
    minHeight = MIN_ROW_COUNT * ICON_SIZE * interfaceScale
    prefHeight = MIN_ROW_COUNT * ICON_SIZE * interfaceScale

    /**
      * @inheritDoc
      */
    override def setEnemyCount(count: Int): Unit = Platform.runLater { () =>
        children.clear()
        for i <- 0 until count do children.add(createEnemyIcon())
    }

    /**
      * @inheritDoc
      */
    override def enemyDefeated(): Unit = 
        children.headOption.foreach(c => children.removeAll(c))

    /**
      * Creates an icon for an ememy
      *
      * @return The created icon as an ImageView
      */
    private def createEnemyIcon() =
        ImageView(JFXImageLoader.loadFromResources("ui/enemy_icon.png", ICON_SIZE, interfaceScale))