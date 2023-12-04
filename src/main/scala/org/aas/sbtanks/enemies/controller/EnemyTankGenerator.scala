package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
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




type EntityControllerWithMv[M, V] =  EntityMvRepositoryContainer[M, V] with EntityControllerRepository[M, V, ?]

class EnemyTankGenerator(entityRepository: EntityControllerWithMv[AnyRef, Node], var tanks: mutable.Queue[ControllableTank], val tileSize: Double, val viewScale: Double, val tileAnimationSpeed: Double = 1) extends Steppable:


    private var timeToSpawn: Double = 3.0



    override def step(delta: Double): this.type =
        timeToSpawn -= delta
        if timeToSpawn <= 0 then
            timeToSpawn = 3.0
            if tanks.isEmpty then
                entityRepository.removeController(this)
            else
               generate(tanks.dequeue())
        this





    def generate(tank: ControllableTank) =

        entityRepository.addModelView(
            tank.setDamageable(false),
            Option(JFXEnemySpawnView(tileSize, viewScale, tileAnimationSpeed))
        )
















