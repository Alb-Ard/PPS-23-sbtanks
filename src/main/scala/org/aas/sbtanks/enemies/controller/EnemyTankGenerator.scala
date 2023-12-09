package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.spawn.EnemyFactory
import org.aas.sbtanks.enemies.view.scalafx.JFXEnemySpawnView
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.{EntityControllerRepository, EntityMvRepositoryContainer}
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

import scala.reflect.ClassTag
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.SteelWall
import org.aas.sbtanks.entities.obstacles.view.ObstacleView
import org.aas.sbtanks.entities.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

import scala.collection.mutable
import org.aas.sbtanks.enemies.spawn.EnemyFactory.withPosition
import org.aas.sbtanks.physics.PhysicsContainer



/**
 * Type alias representing an entity controller repository with model-view access capabilities.
 *
 * This type is a combination of the EntityMvRepositoryContainer and EntityControllerRepository types.
 * It provides a unified interface for managing both entities in terms of their models, views and controllers.
 *
 * @tparam M The type of the entity model.
 * @tparam V The type of the entity view.
 */
type EntityControllerWithMv[M, V] =  EntityMvRepositoryContainer[M, V] with EntityControllerRepository[M, V, ?]

/**
 * A class representing an enemy tank generator that produces and spawns enemy tanks based on a predefined string pattern. It also asynchronously provide
 * each new enemy tank with a suitable new position based on the physic world state
 *
 * @param entityRepository  The entity repository to manage the generated enemy tanks.
 * @param tankString        The string pattern defining the types and order of enemy tanks.
 * @param width             The width of the game board.
 * @param height            The height of the game board.
 * @param tileSize          The size of each game board tile.
 * @param viewScale         The scale factor for rendering.
 * @param tileAnimationSpeed The speed of the tile animation. Default is 1.
 */
class EnemyTankGenerator(using PhysicsContainer)(entityRepository: EntityControllerWithMv[AnyRef, Node], var tankString:String , val width: Double, val height: Double, val tileSize: Double, val viewScale: Double, val tileAnimationSpeed: Double = 1) extends Steppable:

    private var timeToSpawn: Double = 3.0
    private var tanksQueue = mutable.Queue.from(EnemyFactory.createFromString(tankString))

    def remainingEnemyCount = tanksQueue.length

    override def step(delta: Double): this.type =
        timeToSpawn -= delta
        if timeToSpawn <= 0 then
            timeToSpawn = 3.0
            if tanksQueue.isEmpty then
                entityRepository.removeController(this)
            else
                generateFromBuilder(tanksQueue.dequeue())
        this

    private def generateFromBuilder(tankBuilder: EnemyTankBuilder) =
        tankBuilder
            .withPosition(width, height)
            .foreach:
                case t: Tank =>
                    entityRepository.addModelView(
                        t.asInstanceOf[DamageableBehaviour].setDamageable(false),
                        Option(JFXEnemySpawnView(tileSize, viewScale, tileAnimationSpeed))
                    )






















