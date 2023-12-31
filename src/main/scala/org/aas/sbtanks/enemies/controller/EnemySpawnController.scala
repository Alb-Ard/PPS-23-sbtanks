package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.obstacles.view.ObstacleView
import scalafx.scene.layout.Pane
import scalafx.stage.Stage
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import scalafx.scene.Node
import org.aas.sbtanks.enemies.controller.EnemySpawnController.createTankv
import org.aas.sbtanks.enemies.view.EnemySpawnView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.event.EventSource

/**
 * Controller class for managing the spawning behavior of enemy tanks.
 *
 * @param context       The entity repository context.
 * @param entityRepo    The container for entity repository functionality.
 * @param enemyTank     The enemy tank is spawning phase.
 * @param enemyView     The view associated with the enemy spawn.
 * @param viewScale     The scale factor for the view.
 * @param tileSize      The size of each tile in the view.
 * @param tankSpawn     The event source for tank spawning.
 * @param animationSpeed The speed at which the animation should play.
 */
class EnemySpawnController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS])(
        entityRepo: EntityMvRepositoryContainer[AnyRef, Node],
        private val enemyTank: ControllableTank,
        private val enemyView: EnemySpawnView,
        viewScale: Double,
        tileSize: Double,
        tankSpawn: EventSource[Tank],
        animationSpeed: Double
    ) extends Steppable:
    private var timeToSpawn: Double = 4.0

    enemyView.move(enemyTank.positionX * viewScale, enemyTank.positionY * viewScale)
    enemyView.initSpawnAnimation()

    
    override def step(delta: Double): this.type =
        timeToSpawn -= delta
        if timeToSpawn <= 0.0 then
            val newView = createTankv(enemyTank.positionX, enemyTank.positionY, "player", Seq("slow", "basic"), 4D, tileSize, 1D / tileSize, animationSpeed)
            val tank = enemyTank.setDamageable(true)
            tankSpawn(tank)
            entityRepo.replaceView(tank, Option(newView))
        this

object EnemySpawnController:
    def factory(viewScale: Double, tileSize: Double, entityRepo: EntityMvRepositoryContainer[AnyRef, Node], tankSpawn: EventSource[Tank], animationSpeed: Double)(context: EntityRepositoryContext[Stage, ?, ?], tank: ControllableTank, view: EnemySpawnView) =
        new EnemySpawnController(using context)(entityRepo, tank, view, viewScale, tileSize, tankSpawn, animationSpeed)

    def createTankv(x: Double, y: Double, tankType: String, tankAttributes: Seq[String], viewScale: Double, tileSize: Double, pixelSize: Double, animationSpeed: Double) =
        val attributeString = tankAttributes.fold("")((c, n) => c + n + "_")
        val images = Seq("up", "right", "down", "left").map(d => JFXImageLoader.loadFromResources(Seq(
            s"entities/tank/$tankType/${tankType}_tank_${attributeString}${d}_1.png",
            s"entities/tank/$tankType/${tankType}_tank_${attributeString}${d}_2.png"),
            tileSize - pixelSize,
            tileSize,
            viewScale))
        val invincibilityImages = JFXImageLoader.loadFromResources(Seq("effects/invincibility_1.png", "effects/invincibility_2.png"), tileSize, pixelSize)
        JFXTankView(images, invincibilityImages, animationSpeed)