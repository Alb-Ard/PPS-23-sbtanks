package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import scalafx.scene.Node
import scalafx.stage.Stage
import scalafx.scene.layout.Pane
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer._
import scalafx.scene.media.MediaPlayer
import org.aas.sbtanks.physics.PhysicsContainer

/**
 * Controller class for managing enemy tank behavior and AI.
 *
 * @param context    The entity repository context.
 * @param physics    The container for physics-related functionality.
 * @param enemyTank  The enemy tank to.
 * @param enemyView  The view associated with the enemy tank.
 * @param viewScale  The scale factor for the view.
 * @param tileSize   The size of each tile in the view.
 */
class EnemyController[VSK, VS](using context: EntityRepositoryContext[Stage, VSK, VS], physics: PhysicsContainer)(private val enemyTank: ControllableTank, private val enemyView: TankView, viewScale: Double, tileSize: Double, shootDelayTotal: Double)
    extends TankController(enemyTank, enemyView, viewScale, tileSize, shootDelayTotal, Option.empty)
    with AiMovableController(enemyTank.asInstanceOf[Tank with MovementEntity with ShootingEntity], tileSize, () => enemyTank.tankData.speed)
    with Steppable:

    // Initialize enemy tank position and direction
    enemyTank.setPosition(enemyTank.positionX, enemyTank.positionY)
    enemyTank.setDirection(0.0, 0.0)

    override def step(delta: Double) =
        super.step(delta)
        computeNewMovementState()
        shoot()
        this

    override protected def isPlayerBulletShooter = false

object EnemyController:
    private val SHOOT_DELAY = 2D

    def factory(using PhysicsContainer)(viewScale: Double, tileSize: Double, bulletConsumer: (AnyRef, Node) => Any)(oldController: Any, context: EntityRepositoryContext[Stage, ?, ?], tank: ControllableTank, view: TankView) =
        val controller = new EnemyController(using context)(tank, view, viewScale, tileSize, SHOOT_DELAY)
        controller.bulletShot += { (b, v) => bulletConsumer(b, v) }
        controller

        





