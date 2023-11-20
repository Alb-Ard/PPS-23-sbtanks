package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.{EntityMvRepositoryContainer, EntityRepositoryContext}
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

import scala.reflect.ClassTag
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.obstacles.LevelObstacle.SteelWall
import org.aas.sbtanks.obstacles.view.ObstacleView
import org.aas.sbtanks.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

import scala.collection.immutable.Queue



class EnemyTankGenerator(entityRepository: EntityMvRepositoryContainer[AnyRef, Node], val tank: ControllableTank, val tileSize: Double = 16D, val viewScale: Double = 4D, val tileAnimationSpeed: Double = 1):

    private var timeToSpawn: Double = 3.0



    def loadTank(): this.type =
        this.generate()
        this

    def generate() =

        val v = LevelObstacle.SteelWall(2.0, 6.0).map(o => {
            JFXObstacleView.createAnimated(JFXImageLoader.loadFromResources(o.imagesPath, tileSize, viewScale), tileAnimationSpeed)
        })


        entityRepository.addModelView(
            tank,
            Option(v.head)
        )
















