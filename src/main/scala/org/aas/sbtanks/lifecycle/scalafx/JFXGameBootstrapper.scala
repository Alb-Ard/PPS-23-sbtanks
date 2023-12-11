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
import org.aas.sbtanks.enemies.controller.EnemyTankGenerator
import org.aas.sbtanks.entities.powerups.PowerUpChainBinder
import org.aas.sbtanks.entities.powerups.controller.PowerUpBinderController
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.lifecycle.PointsContainer
import org.aas.sbtanks.lifecycle.PointsGiver
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityMvRepositoryFactory.addDefaultControllerFactories
import scalafx.scene.media.MediaPlayer
import org.aas.sbtanks.lifecycle.scalafx.JFXGameBootstrapper._
import org.aas.sbtanks.player.PlayerTank

/**
  * A class used to manage all components required for a game
  *
  * @param context A given view context used to show the game
  * @param interfaceScale A scaling factor for the game interface
  * @param windowSize The window size, in pixels
  */
class JFXGameBootstrapper(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)):
    private val TANK_ANIMATION_SPEED = 16D
    private val IS_DEBUG = true

    /**
     * An event that is invoked when the game has ended.
     */
    val gameEnded = EventSource[GameEndedArgs]

    private val powerupPickupped = EventSource[Option[PowerUp[Tank]]]
    private val tankSpawned = EventSource[Tank]

    given PhysicsContainer = PhysicsWorld
    given PointsContainer = PointsManager

    private val entityRepository = JFXEntityMvRepositoryFactory.create(IS_DEBUG).addDefaultControllerFactories(powerupPickupped, tankSpawned, TANK_ANIMATION_SPEED)
    private val playerSidebar = JFXPlayerSidebarView.create(interfaceScale, windowSize(1))
    private val levelFactory = JFXLevelFactory(JFXEntityMvRepositoryFactory.TILE_SIZE, JFXEntityMvRepositoryFactory.VIEW_SCALE, 1, TANK_ANIMATION_SPEED)
    private val levelLoader = LevelLoader()
    private val levels = levelLoader.getLevelSeq(5)
    private val levelSequencer = LevelSequencer[AnyRef, Node](levels, levelFactory, entityRepository)
    private val gameLoop = GameLoop(entityRepository, Seq(entityRepository))
    private val pauseUiView = JFXPauseMenu(interfaceScale, windowSize)
    private val pointsGiver = PointsGiver.create(entityRepository, powerupPickupped)

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
        val playerDeathController = new JFXPlayerDeathController(entityRepository, levelSequencer, ViewSlot.Ui)
        playerDeathController.playerDied += { _ => endGame(EndGameType.Lose) }
        val pauseController = new JFXPauseController(gameLoop, pauseUiView):
            override def quit() =
                endGame(EndGameType.Exit)
        var enemyGenerator = Option.empty[EnemyTankGenerator]
        var binderController = Option.empty[PowerUpBinderController]
        levelSequencer.levelChanged += { (level, enemyString) => 
            enemyGenerator.foreach(entityRepository.removeController)
            binderController.foreach(entityRepository.removeController)
            enemyGenerator = Option(new EnemyTankGenerator(entityRepository, enemyString, level.size, level.size, JFXEntityMvRepositoryFactory.TILE_SIZE, JFXEntityMvRepositoryFactory.VIEW_SCALE))
            binderController = Option(new PowerUpBinderController(entityRepository, level.size, level.size, PowerUpChainBinder[Tank](), powerupPickupped, tankSpawned, JFXEntityMvRepositoryFactory.TILE_SIZE, JFXEntityMvRepositoryFactory.VIEW_SCALE))
            entityRepository.addController(enemyGenerator.get)
                .addController(binderController.get)
            playerUiViewController.setEnemyCount(enemyGenerator.get.remainingEnemyCount) 
            playerUiViewController.setCompletedLevelCount(levelSequencer.completedLevelCount)
            entityRepository.entitiesOfModelType[PlayerTank].foreach(e => tankSpawned(e(0)))
            entityRepository.modelViewRemoved += { (m, _) => m match
                case t: Tank if !t.isInstanceOf[PlayerTank] 
                    && entityRepository.entitiesOfModelType[Tank].filterNot(_(0).isInstanceOf[PlayerTank]).length <= 0
                    && enemyGenerator.map(_.remainingEnemyCount).getOrElse(0) <= 0 => levelSequencer.completeLevel()
                case _ => ()   
            }
        }
        entityRepository.addController(playerDeathController)
            .addController(playerUiViewController)
            .addController(pauseController)
        cleanup = Option(() => {
            entityRepository.removeController(playerDeathController)
                .removeController(playerUiViewController)
                .removeController(pauseController)
            enemyGenerator.foreach(entityRepository.removeController)
            binderController.foreach(entityRepository.removeController)
        })
        levelSequencer.start()
        gameLoop.setPaused(false)
        this
    
    def endGame(endType: EndGameType): this.type =
        gameLoop.setPaused(true)
        entityRepository.clear().executeQueuedCommands()
        cleanup.foreach(_())
        val completedLevelCount = levelSequencer.completedLevelCount
        levelSequencer.reset()
        val points = summon[PointsContainer].amount
        summon[PointsContainer].resetAmount()
        gameEnded(GameEndedArgs(endType, completedLevelCount, points))
        this

object JFXGameBootstrapper:
    enum EndGameType:
        case Win
        case Exit
        case Lose

    case class GameEndedArgs(val endType: EndGameType, val completedLevelCount: Int, val points: Int)
