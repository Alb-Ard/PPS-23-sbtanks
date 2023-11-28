package org.aas.sbtanks

import scalafx.application.JFXApp3
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.animation.AnimationTimer
import org.aas.sbtanks.player.controller.scalafx.{JFXPlayerDeathController, JFXPlayerInputController, JFXPlayerTankController}
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.scalafx.JFXBulletController
import org.aas.sbtanks.enemies.controller.EnemyController
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityControllerRepository
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityViewAutoManager
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.obstacles.LevelObstacleController
import org.aas.sbtanks.level.scalafx.JFXLevelFactory
import org.aas.sbtanks.entities.repository.DestroyableEntityAutoManager
import scalafx.scene.Node
import org.aas.sbtanks.entities.repository.EntityRepositoryTagger
import org.aas.sbtanks.entities.repository.EntityControllerReplacer
import org.aas.sbtanks.entities.repository.EntityColliderAutoManager
import org.aas.sbtanks.player.view.ui.scalafx.JFXPlayerSidebarView
import scalafx.scene.layout.Pane
import org.aas.sbtanks.player.controller.PlayerUiViewController
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.entities.repository.context.scalafx.JFXEntityRepositoryContextInitializer
import org.aas.sbtanks.common.ViewSlot
import org.aas.sbtanks.level.LevelLoader
import org.aas.sbtanks.lifecycle.PointsManager
import org.aas.sbtanks.lifecycle.GameLoop
import org.aas.sbtanks.lifecycle.view.scalafx.JFXPauseMenu
import org.aas.sbtanks.entities.repository.EntityRepositoryPausableAdapter
import org.aas.sbtanks.common.Pausable
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityMvRepositoryFactory
import org.aas.sbtanks.lifecycle.scalafx.JFXGameBootstrapper
import org.aas.sbtanks.lifecycle.view.scalafx.JFXMainMenu
import scalafx.application.Platform

object Main extends JFXApp3 with scalafx.Includes:
    val windowSize = (1280, 720)
    val interfaceScale = 4D

    override def start(): Unit = 
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = windowSize(0)
            height = windowSize(1)
            scene = new Scene:
                fill = Color.BLACK
                stylesheets.add(getClass().getResource("/ui/style.css").toExternalForm())

        given EntityRepositoryContext[Stage, ViewSlot, Pane] = EntityRepositoryContext(stage).switch(JFXEntityRepositoryContextInitializer.ofView(ViewSlot.Ui))
        val bootstrapper = JFXGameBootstrapper(interfaceScale, windowSize)

        val mainMenu = JFXMainMenu(interfaceScale)
        summon[EntityRepositoryContext[Stage, ViewSlot, Pane]].viewSlots(ViewSlot.Ui).children.add(mainMenu)
        mainMenu.startSinglePlayerGameRequested += { _ => bootstrapper.startGame() }
        mainMenu.optionsRequested += { _ => ??? }
        mainMenu.quitRequested += { _ => Platform.exit() }
