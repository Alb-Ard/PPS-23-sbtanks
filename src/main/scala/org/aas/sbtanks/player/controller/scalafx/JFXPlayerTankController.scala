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
import scalafx.scene.layout.Pane
import scalafx.Includes
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import org.aas.sbtanks.player.view.ui.PlayerHealthView

abstract class JFXPlayerTankController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS])(tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double, tileSize: Double)
    extends TankInputController(tank, view, speedMultiplier, viewScale, tileSize, JFXPlayerInputController())
    with EntityRepositoryContextAware
    with Includes:

    registerSceneEventHandlers(context.viewController)

    private def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)

object JFXPlayerTankController:
    def factory(speedMultiplier: Double, viewScale: Double, tileSize: Double, bulletConsumer: (AnyRef, Node) => Any)(context: EntityRepositoryContext[Stage, ?, ?], tank: ControllableTank, view: TankView) =
        new JFXPlayerTankController(using context)(tank, speedMultiplier, view, viewScale, tileSize):

            var shootDelay = 0.0

            override def step(delta: Double) =
                shootDelay += 0.1
                super.step(delta)

            override def shoot() =
                if(shootDelay >= 5.0)
                    val bullet = tank.shoot(1, true).head
                    val bulletView = new JFXBulletView(JFXImageLoader.loadFromResources("entities/bullet/bullet.png", tileSize, viewScale))
                    bulletConsumer(bullet, bulletView)
                    shootDelay = 0.0
                this