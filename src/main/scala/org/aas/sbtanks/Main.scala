package org.aas.sbtanks

import scalafx.application.JFXApp3
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.animation.AnimationTimer
import org.aas.sbtanks.player.controller.scalafx.{JFXPlayerDeathController, JFXPlayerInputController, JFXPlayerTankController}
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import scalafx.scene.layout.Pane
import org.aas.sbtanks.player.controller.PlayerUiViewController
import org.aas.sbtanks.lifecycle.{GameLoop, LevelSequencer, PointsManager, SavedDataManager}
import org.aas.sbtanks.entities.repository.context.scalafx.JFXEntityRepositoryContextInitializer
import org.aas.sbtanks.common.ViewSlot
import org.aas.sbtanks.level.LevelLoader
import org.aas.sbtanks.lifecycle.view.scalafx.{JFXGameOverView, JFXMainMenu, JFXOptionsMenu, JFXPauseMenu}
import org.aas.sbtanks.lifecycle.scalafx.JFXGameBootstrapper
import scalafx.application.Platform
import scalafx.beans.property.IntegerProperty
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer
import scalafx.util.Duration
import scalafx.scene.media.MediaPlayer

object Main extends JFXApp3 with scalafx.Includes:
    val INTERFACE_SCALE = 4D
    
    val windowSize = (IntegerProperty(1280), IntegerProperty(720))

    override def start(): Unit =
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = windowSize(0).value
            height = windowSize(1).value
            scene = new Scene:
                fill = Color.Black
                stylesheets.add(getClass().getResource("/ui/style.css").toExternalForm())
        windowSize(0) <== stage.scene.value.window.value.width
        windowSize(1) <== stage.scene.value.window.value.height
        given EntityRepositoryContext[Stage, ViewSlot, Pane] = EntityRepositoryContext(stage)
        JFXMediaPlayer.loadSettingsFromDisk().precache()
        launchMainMenu()
    
    private def launchMainMenu(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(): Unit = 
        def startGame(): Unit = launchGame()
        def setHideMarker(mediaPlayer: MediaPlayer, name: String): Unit = 
            mediaPlayer.media.markers.addOne(name -> mediaPlayer.totalDuration.value.divide(2))

        context.switch(JFXEntityRepositoryContextInitializer.ofView(ViewSlot.Ui))
        val mainMenu = JFXMainMenu(INTERFACE_SCALE, windowSize)
        context.viewSlots(ViewSlot.Ui).children.add(mainMenu)
        mainMenu.startSinglePlayerGameRequested += { _ => 
            mainMenu.disable = true
            val mediaPlayer = JFXMediaPlayer.play(JFXMediaPlayer.MEDIA_START_MUSIC)
            val hideMenuMarkerName = "hide-menu"
            mediaPlayer.onEndOfMedia = () => startGame()
            mediaPlayer.onPlaying = () => setHideMarker(mediaPlayer, hideMenuMarkerName)
            mediaPlayer.onMarker = v => v.getMarker().getKey() match
                case hideMenuMarkerName => 
                    mainMenu.visible = false
                    mediaPlayer.media.markers.remove(hideMenuMarkerName)
                case null => ()
        }
        mainMenu.optionsRequested += { _ => launchOptionsMenu() }
        mainMenu.quitRequested += { _ => Platform.exit() }

    private def launchGame(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(): Unit =
        val bootstrapper = JFXGameBootstrapper(INTERFACE_SCALE, windowSize)
        bootstrapper.gameEnded += { _ => launchMainMenu() }
        bootstrapper.restartedGame += { _ => launchGame()}
        bootstrapper.startGame()

    private def launchOptionsMenu(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(): Unit =
        context.switch(JFXEntityRepositoryContextInitializer.ofView(ViewSlot.Ui))
        val optionsMenu = JFXOptionsMenu(INTERFACE_SCALE, windowSize)
        context.viewSlots(ViewSlot.Ui).children.add(optionsMenu)
        optionsMenu.mainMenuRequested += { _ =>
            JFXMediaPlayer.saveSettingsToDisk()
            launchMainMenu() 
        }
        optionsMenu.resetHighScoreRequested += { _ => SavedDataManager.resetHighScore() }
