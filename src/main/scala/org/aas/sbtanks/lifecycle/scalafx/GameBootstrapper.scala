package org.aas.sbtanks.lifecycle.scalafx

import org.aas.sbtanks.entities.repository.scalafx.JFXEntityMvRepositoryFactory
import org.aas.sbtanks.player.view.ui.scalafx.JFXPlayerSidebarView
import org.aas.sbtanks.player.controller.PlayerUiViewController
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.common.ViewSlot
import scalafx.stage.Stage
import scalafx.scene.layout.Pane
import scalafx.scene.Node
import org.aas.sbtanks.level.scalafx.JFXLevelFactory
import org.aas.sbtanks.level.LevelLoader
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerDeathController
import org.aas.sbtanks.entities.repository.context.scalafx.JFXEntityRepositoryContextInitializer
import org.aas.sbtanks.lifecycle.GameLoop
import org.aas.sbtanks.lifecycle.view.scalafx.JFXPauseMenu

/**
  * A class used to manage all components required for a game
  *
  * @param context A given view context used to show the game
  * @param interfaceScale A scaling factor for the game interface
  * @param windowSize The window size, in pixels
  */
class JFXGameBootstrapper(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(interfaceScale: Double, windowSize: (Int, Int)):
    private val entityRepository = JFXEntityMvRepositoryFactory.create()
    private val playerSidebar = JFXPlayerSidebarView.create(interfaceScale, windowSize(1))
    private val levelFactory = JFXLevelFactory(JFXEntityMvRepositoryFactory.TILE_SIZE, JFXEntityMvRepositoryFactory.VIEW_SCALE, 1)
    private val levelLoader = LevelLoader()
    private val levels = levelLoader.getLevelSeq(5)
    private val levelSequencer = LevelSequencer[AnyRef, Node](levels(0), levelFactory, entityRepository)
    private val gameLoop = GameLoop(entityRepository, Seq(entityRepository))
    private val pauseUiView = JFXPauseMenu(gameLoop)

    /**
      * Starts the game
      *
      * @return This game boostrapper
      */
    def startGame(): this.type =
        context.switch(JFXEntityRepositoryContextInitializer.ofLevel(ViewSlot.Game, ViewSlot.Ui))
        val playerUiViewController = new PlayerUiViewController[AnyRef, Node, Stage, ViewSlot, Pane](entityRepository, playerSidebar, ViewSlot.Ui):
            override protected def addViewToContext(container: Pane) =
                container.children.add(playerSidebar)
        val playerDeathController = new JFXPlayerDeathController(entityRepository, levelSequencer, ViewSlot.Ui):
            override protected def setupGameoverContext(currentContext: EntityRepositoryContext[Stage, ViewSlot, Pane]) = 
                currentContext.switch(JFXEntityRepositoryContextInitializer.ofView(ViewSlot.Ui))
        levelSequencer.levelChanged += { (_, enemyCount) => 
            playerUiViewController.setEnemyCount(enemyCount) 
            playerUiViewController.setCompletedLevelCount(levelSequencer.completedLevelCount)
        }
        entityRepository.addController(playerDeathController)
        entityRepository.addController(playerUiViewController)
        levelSequencer.start()
        gameLoop.setPaused(false)
        this
