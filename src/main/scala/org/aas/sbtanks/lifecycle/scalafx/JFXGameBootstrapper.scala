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
import org.aas.sbtanks.lifecycle.controller.scalafx.JFXPauseController
import org.aas.sbtanks.event.EventSource
import scalafx.beans.property.IntegerProperty
import org.aas.sbtanks.lifecycle.PointsManager
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer
import org.aas.sbtanks.physics.PhysicsContainer
import org.aas.sbtanks.physics.PhysicsWorld

/**
  * A class used to manage all components required for a game
  *
  * @param context A given view context used to show the game
  * @param interfaceScale A scaling factor for the game interface
  * @param windowSize The window size, in pixels
  */
class JFXGameBootstrapper(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)):
    val IS_DEBUG = true

    val gameEnded = EventSource[Unit]
    val restartedGame = EventSource[Unit]

    given PhysicsContainer = PhysicsWorld
    private val entityRepository = JFXEntityMvRepositoryFactory.create(IS_DEBUG)
    private val playerSidebar = JFXPlayerSidebarView.create(interfaceScale, windowSize(1))
    private val levelFactory = JFXLevelFactory(JFXEntityMvRepositoryFactory.TILE_SIZE, JFXEntityMvRepositoryFactory.VIEW_SCALE, 1, 16)
    private val levelLoader = LevelLoader()
    private val levels = levelLoader.getLevelSeq(5)
    private val levelSequencer = LevelSequencer[AnyRef, Node](levels(0), levelFactory, entityRepository)
    private val gameLoop = GameLoop(entityRepository, Seq(entityRepository))
    private val pauseUiView = JFXPauseMenu(interfaceScale, windowSize)

    private var cleanup: Option[() => Any] = Option.empty

    /**
      * Starts the game
      *
      * @return This game boostrapper
      */
    def startGame(): this.type =
        context.switch(JFXEntityRepositoryContextInitializer.ofLevel(ViewSlot.Game, ViewSlot.Ui, ViewSlot.Overlay))
        val playerUiViewController = new PlayerUiViewController[AnyRef, Node, Stage, ViewSlot, Pane](entityRepository, playerSidebar, ViewSlot.Ui):
            override protected def addViewToContext(container: Pane) =
                container.children.add(playerSidebar)
        val playerDeathController = new JFXPlayerDeathController(entityRepository, levelSequencer, ViewSlot.Ui):
            override protected def setupGameoverContext(currentContext: EntityRepositoryContext[Stage, ViewSlot, Pane]) =
                JFXMediaPlayer.play(JFXMediaPlayer.GAME_OVER_SFX)
                currentContext.switch(JFXEntityRepositoryContextInitializer.ofView(ViewSlot.Ui))
            override protected def restart(currentContext: EntityRepositoryContext[Stage, ViewSlot, Pane]): this.type =
                restartedGame(())
                this
        val pauseController = new JFXPauseController(gameLoop, pauseUiView):
            override def quit() =
                endGame()
        levelSequencer.levelChanged += { (_, enemyCount) => 
            playerUiViewController.setEnemyCount(enemyCount) 
            playerUiViewController.setCompletedLevelCount(levelSequencer.completedLevelCount)
        }
        entityRepository.addController(playerDeathController)
            .addController(playerUiViewController)
            .addController(pauseController)
        cleanup = Option(() => {
            entityRepository.removeController(playerDeathController)
                .removeController(playerUiViewController)
                .removeController(pauseController)
        })
        PointsManager.addAmount(500)
        levelSequencer.start()
        gameLoop.setPaused(false)
        this
    
    def endGame(): this.type =
        gameLoop.setPaused(true)
        entityRepository.clear()
        cleanup.foreach(_())
        levelSequencer.reset()
        gameEnded(())
        this