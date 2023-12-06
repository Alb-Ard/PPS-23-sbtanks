package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.entities.bullet.view.scalafx.JFXBulletView
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.controller.TankInputController
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.scene.Node
import scalafx.Includes
import org.aas.sbtanks.resources.scalafx.{JFXImageLoader, JFXMediaPlayer}
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer._
import org.aas.sbtanks.physics.PhysicsContainer
import scalafx.scene.media.MediaPlayer
import javafx.scene.media.MediaPlayer.Status
import scalafx.scene.media.AudioClip
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.entities.bullet.view.BulletView
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet
import scalafx.util.Duration
import scalafx.Includes._

/**
 * a controller used to handle the player's tank, handling events such as key pressed and firing bullets.
 *
 * @param context The current context.
 * @param tank the player tank.
 * @param speedMultiplier the player tank speed multiplier to match model with view.
 * @param view the player tank view.
 * @param viewScale the scale of the view.
 * @param tileSize the size of the tile.
 * @tparam VSK
 * @tparam VS
 */
class JFXPlayerTankController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS], physics: PhysicsContainer)(tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double, tileSize: Double)
    extends TankInputController(tank, view, speedMultiplier, viewScale, tileSize, JFXPlayerInputController(), JFXMediaPlayer.play(JFXMediaPlayer.PLAYER_MOVE_SFX, (p: MediaPlayer) => p.setLooping(true).setLoopDuration(0.05D)))
    with EntityRepositoryContextAware:

    registerSceneEventHandlers(context.viewController)

    private def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)

object JFXPlayerTankController:
    def factory(using physics: PhysicsContainer)(speedMultiplier: Double, viewScale: Double, tileSize: Double, bulletConsumer: (AnyRef, Node) => Any)(context: EntityRepositoryContext[Stage, ?, ?], tank: ControllableTank, view: TankView) =
        val controller = new JFXPlayerTankController(using context)(tank, speedMultiplier, view, viewScale, tileSize)
        controller.bulletShot += { (b, v) => bulletConsumer(b, v) }
        controller