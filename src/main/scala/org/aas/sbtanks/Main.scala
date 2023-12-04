package org.aas.sbtanks

import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.common.ViewSlot
import org.aas.sbtanks.common.view.scalafx.JFXImageViewAnimator
import org.aas.sbtanks.enemies.controller.EnemyController
import org.aas.sbtanks.enemies.controller.EnemySpawnController
import org.aas.sbtanks.enemies.controller.EnemyTankGenerator
import org.aas.sbtanks.enemies.spawn.EnemyFactory
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.scalafx.JFXBulletController
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacleController
import org.aas.sbtanks.entities.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.entities.repository.DestroyableEntityAutoManager
import org.aas.sbtanks.entities.repository.EntityColliderAutoManager
import org.aas.sbtanks.entities.repository.EntityControllerReplacer
import org.aas.sbtanks.entities.repository.EntityRepositoryTagger
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.context.scalafx.JFXEntityRepositoryContextInitializer
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityControllerRepository
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.scalafx.JFXEntityViewAutoManager
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.level.scalafx.JFXLevelFactory
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.player.PlayerTankBuilder
import org.aas.sbtanks.player.PlayerTankData
import org.aas.sbtanks.player.controller.PlayerUiViewController
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerDeathController
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerInputController
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerTankController
import org.aas.sbtanks.player.view.ui.scalafx.JFXPlayerSidebarView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp3
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.input.KeyEvent
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Pane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Region
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.Stage
import scalafx.geometry.Pos
import org.aas.sbtanks.player.controller.PlayerUiViewController
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank
import org.aas.sbtanks.entities.repository.context.scalafx.JFXEntityRepositoryContextInitializer
import org.aas.sbtanks.common.ViewSlot
import org.aas.sbtanks.enemies.spawn.EnemyFactory
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp
import org.aas.sbtanks.entities.powerups.PowerUpChainBinder
import org.aas.sbtanks.entities.powerups.controller.PowerUpBinderController
import org.aas.sbtanks.event.EventSource

import scala.collection.mutable
import org.aas.sbtanks.entities.powerups.controller.PickablePowerUp
import org.aas.sbtanks.entities.powerups.controller.PowerUpController
import org.aas.sbtanks.entities.powerups.controller.PowerUpBinderController

object Main extends JFXApp3 with scalafx.Includes:
    val viewScale = 4D
    val tileSize = 16D
    val tankUnitMoveSpeed = 1D / tileSize
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

        given EntityRepositoryContext[Stage, ViewSlot, Pane] = EntityRepositoryContext(stage).switch(JFXEntityRepositoryContextInitializer.ofLevel(ViewSlot.Game, ViewSlot.Ui))
        val entityRepository = new JFXEntityMvRepositoryContainer()
            with JFXEntityControllerRepository
            with JFXEntityViewAutoManager(ViewSlot.Game)
            with EntityControllerReplacer[AnyRef, Node, EntityRepositoryContext[Stage, ViewSlot, Pane]]
            with DestroyableEntityAutoManager[AnyRef, Node]
            with EntityRepositoryTagger[AnyRef, Node, Int]
            with EntityColliderAutoManager[AnyRef, Node]
            with EntityRepositoryContextAware

        val pickup = EventSource[PowerUp[Tank]]

        entityRepository.registerControllerFactory(m => m.isInstanceOf[PlayerTank], JFXPlayerTankController.factory(tankUnitMoveSpeed, viewScale * tileSize, (bulletModel, bulletView) => entityRepository.addModelView(bulletModel, Option(bulletView)), tileSize))
            .registerControllerFactory(m => m.isInstanceOf[LevelObstacle], LevelObstacleController.factory(viewScale * tileSize))
            .registerControllerFactory(m => m.isInstanceOf[Tank] && !m.isInstanceOf[PlayerTank] && !m.asInstanceOf[DamageableBehaviour].isDamageable, EnemySpawnController.factory(viewScale * tileSize, tileSize, entityRepository))
            .registerControllerReplacer(m => m.isInstanceOf[Tank] && !m.isInstanceOf[PlayerTank] && m.asInstanceOf[DamageableBehaviour].isDamageable, EnemyController.factory(viewScale * tileSize))
            .registerControllerFactory(m => m.isInstanceOf[Bullet], JFXBulletController.factory())
            .registerControllerFactory(m => m.isInstanceOf[PickablePowerUp[?]], PowerUpController.factory[Tank](viewScale * tileSize, entityRepository, pickup))


        val playerSidebar = JFXPlayerSidebarView.create(interfaceScale, windowSize(1))
        val playerUiViewController = new PlayerUiViewController[AnyRef, Node, Stage, ViewSlot, Pane](entityRepository, playerSidebar, ViewSlot.Ui):
            override protected def addViewToContext(container: Pane) =
                container.children.add(playerSidebar)

        entityRepository.addController(playerUiViewController)

        // ** TEST **
        val level1 = ("UUUUUUU" +
            "U-TTT-U" +
            "U-SwS-U" +
            "U--P--U" +
            "U-WWW-U" +
            "U-WBW-U" +
            "UUUUUUU", 7, 10)
        val level2 = ("UUUUUUU" +
            "U-----U" +
            "UTSWSTU" +
            "U-P---U" +
            "U-WWW-U" +
            "U-WBW-U" +
            "UUUUUUU", 7, 10)
        // **********
        val levelFactory = JFXLevelFactory(tileSize, viewScale, 1)

        levelFactory.createFromString("UUUUUUUUUUU" +
                                      "U--TTTTT--U" +
                                      "U--SSwSS--U" +
                                      "U--SwwwS--U" +
                                      "U--TSTST--U" +
                                      "U----P----U" +
                                      "U--SwwwS--U" +
                                      "U--TSTST--U" +
                                      "U--WWWWW--U" +
                                      "U--WWBWW--U" +
                                      "UUUUUUUUUUU", 11, entityRepository)


        val levelSequencer = LevelSequencer[AnyRef, Node](Seq(level1, level2), levelFactory, entityRepository)
        /*
        levelSequencer.levelChanged += { (_, enemyCount) =>
            playerUiViewController.setEnemyCount(enemyCount)
            playerUiViewController.setCompletedLevelCount(levelSequencer.completedLevelCount)
        }
        */

        val playerDeathController = new JFXPlayerDeathController(entityRepository, levelSequencer, ViewSlot.Ui):
            override protected def setupGameoverContext(currentContext: EntityRepositoryContext[Stage, ViewSlot, Pane]) =
                currentContext.switch(JFXEntityRepositoryContextInitializer.ofView(ViewSlot.Ui))
        entityRepository.addController(playerDeathController)

        //levelSequencer.start()

        //val enemies = EnemyFactory.createFromString("BBB", 11, 11, 1).map(_.asInstanceOf[ControllableTank])

        val g = new EnemyTankGenerator(entityRepository, "B||BB", 11, 11, tileSize, viewScale)

        entityRepository.addController(g)

        val binder = new PowerUpChainBinder[Tank]

        val p = new PowerUpBinderController(entityRepository, binder, pickup)

        /*
            TODO: how to pass entities for registration? (e.g. event)
         */

        //p.registerEntities(enemies)

        entityRepository.addController(p)



        var lastTimeNanos = System.nanoTime().doubleValue
        // ** TEST **
        //var testTime = 2D
        //val currentPlayer = entityRepository.entitiesOfModelType[PlayerTank with DamageableBehaviour].head(0)
        // **********
        val updateTimer = AnimationTimer(_ => {
            val currentTimeNanos = System.nanoTime().doubleValue
            val deltaTime = (currentTimeNanos - lastTimeNanos).doubleValue / 1000D / 1000D / 1000D
            entityRepository.step(deltaTime).executeQueuedCommands()
            lastTimeNanos = currentTimeNanos
            // ** TEST **
            //if testTime > 0 && testTime - deltaTime < 0 then
            //    //levelSequencer.completeLevel()
            //    currentPlayer.damage(1)
            //    testTime = 2D
            //testTime -= deltaTime
            // **********
        })
        updateTimer.start()