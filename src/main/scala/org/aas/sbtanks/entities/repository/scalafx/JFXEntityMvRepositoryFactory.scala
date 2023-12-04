package org.aas.sbtanks.entities.repository.scalafx

import scalafx.scene.Node
import scalafx.stage.Stage
import scalafx.scene.layout.Pane

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.EntityRepositoryPausableAdapter
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.EntityColliderAutoManager
import org.aas.sbtanks.entities.repository.EntityRepositoryTagger
import org.aas.sbtanks.entities.repository.DestroyableEntityAutoManager
import org.aas.sbtanks.entities.repository.EntityControllerReplacer
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacleController
import org.aas.sbtanks.common.ViewSlot
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerTankController
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.enemies.controller.EnemyController
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.scalafx.JFXBulletController
import org.aas.sbtanks.entities.repository.EntityControllerRepository
import org.aas.sbtanks.entities.repository.EntityViewAutoManager

/**
  * A factory used to create an entity repository with the default extensions
  */
object JFXEntityMvRepositoryFactory:
    /**
      * The default view context used by the repositories this factory creates
      */
    type DefaultContext = EntityRepositoryContext[Stage, ViewSlot, Pane]

    /**
      * The complete type of the repositories this factory creates
      */
    type DefaultEntityRepository = JFXEntityMvRepositoryContainer 
        with EntityControllerRepository[AnyRef, Node, DefaultContext]
        with EntityViewAutoManager[Node]
        with EntityControllerReplacer[AnyRef, Node, DefaultContext]
        with DestroyableEntityAutoManager[AnyRef, Node]
        with EntityRepositoryTagger[AnyRef, Node, Int]
        with EntityColliderAutoManager[AnyRef, Node]
        with EntityRepositoryPausableAdapter[AnyRef, Node, DefaultContext]
        with EntityRepositoryContextAware[Stage, ViewSlot, Pane]

    /**
      * A scaling factor for the game graphics
      */
    val VIEW_SCALE = 4D
    /**
      * The size, in pixels, of a game grid tile
      */
    val TILE_SIZE = 16D

    private val TANK_UNIT_MOVE_SPEED = 1D / TILE_SIZE
    private val BULLET_UNIT_MOVE_SPEED = TANK_UNIT_MOVE_SPEED * 2D
    
    /**
      * Creates an entity repository with the default extensions, controller factories and replacers
      *
      * @param context A given view context used for the extensions
      * @return The created entity repository
      */
    def create(using context: DefaultContext)(): DefaultEntityRepository =
        val entityRepository = new JFXEntityMvRepositoryContainer()
            with JFXEntityControllerRepository
            with JFXEntityViewAutoManager(ViewSlot.Game)
            with EntityControllerReplacer[AnyRef, Node, DefaultContext]
            with DestroyableEntityAutoManager[AnyRef, Node]
            with EntityRepositoryTagger[AnyRef, Node, Int]
            with EntityColliderAutoManager[AnyRef, Node]
            with EntityRepositoryPausableAdapter[AnyRef, Node, DefaultContext]
            with EntityRepositoryContextAware
        entityRepository.registerControllerFactory(m => m.isInstanceOf[PlayerTank], JFXPlayerTankController.factory(TANK_UNIT_MOVE_SPEED, VIEW_SCALE, TILE_SIZE, (bulletModel, bulletView) => entityRepository.addModelView(bulletModel, Option(bulletView))))
                .registerControllerFactory(m => m.isInstanceOf[LevelObstacle], LevelObstacleController.factory(VIEW_SCALE * TILE_SIZE))
                .registerControllerFactory(m => m.isInstanceOf[Tank] && !m.isInstanceOf[PlayerTank], EnemyController.factory(VIEW_SCALE, TILE_SIZE))
                .registerControllerFactory(m => m.isInstanceOf[Bullet], JFXBulletController.factory(BULLET_UNIT_MOVE_SPEED, VIEW_SCALE, TILE_SIZE))