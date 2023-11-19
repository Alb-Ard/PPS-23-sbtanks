package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.controller.EnemyTankGenerator.createTankv
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



class EnemyTankGenerator(entityRepository: EntityMvRepositoryContainer[AnyRef, Node], val tank: ControllableTank, val tileSize: Double = 16D, val viewScale: Double = 4D, val tileAnimationSpeed: Double = 1) extends Steppable:

    private var timeToSpawn: Double = 3.0



    override def step(delta: Double): this.type =
        timeToSpawn -= delta
        if timeToSpawn <= 0.0 then
            val newView = createTankv(tank.positionX, tank.positionY, "player", Seq("slow", "basic"), viewScale, tileSize, 1D / tileSize)
            entityRepository.replaceView(tank, Option(newView))
        this


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


object EnemyTankGenerator:
    def createTankv(x: Double, y: Double, tankType: String, tankAttributes: Seq[String], viewScale: Double, tileSize: Double, pixelSize: Double) =
        val attributeString = tankAttributes.fold("")((c, n) => c + n + "_")
        val images = Seq("up", "right", "down", "left").map(d => JFXImageLoader.loadFromResources(Seq(
            s"entities/tank/$tankType/${tankType}_tank_${attributeString}${d}_1.png",
            s"entities/tank/$tankType/${tankType}_tank_${attributeString}${d}_2.png"),
            tileSize - pixelSize,
            tileSize,
            viewScale))
        JFXTankView(images, tileSize)














