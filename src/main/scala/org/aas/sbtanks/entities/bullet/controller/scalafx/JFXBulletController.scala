package org.aas.sbtanks.entities.bullet.controller.scalafx

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.BulletController
import org.aas.sbtanks.entities.bullet.view.scalafx.JFXBulletView
import scalafx.scene.layout.Pane
import scalafx.stage.Stage
import org.aas.sbtanks.entities.bullet.controller.BulletController.CompleteBullet
import org.aas.sbtanks.physics.PhysicsContainer

/**
 * JFX implementation of BulletController.
 *
 * @param PhysicsContainer the physics container used by application.
 * @param bullet a complete bullet's model that is moving
 * @param bulletView a view of said bullet
 * @param speedMultiplier a double value that determines the speed of the bullet in View
 * @param viewScale the scale of the view
 * @param tileSize the size of each tile of the view
 */
class JFXBulletController(using PhysicsContainer)(bullet: CompleteBullet, bulletView: JFXBulletView, speedMultiplier: Double, viewScale: Double, tileSize: Double)
    extends BulletController(bullet, bulletView, speedMultiplier, viewScale, tileSize)

object JFXBulletController:
    def factory(using PhysicsContainer)(speedMultiplier: Double, viewScale: Double, tileSize: Double)(context: EntityRepositoryContext[?, ?, ?], bullet: CompleteBullet, bulletView: JFXBulletView) =
        new JFXBulletController(bullet, bulletView, speedMultiplier, viewScale, tileSize)
