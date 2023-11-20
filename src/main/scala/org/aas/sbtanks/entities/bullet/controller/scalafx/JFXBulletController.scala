package org.aas.sbtanks.entities.bullet.controller.scalafx

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.BulletController
import org.aas.sbtanks.entities.bullet.view.scalafx.JFXBulletView
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

class JFXBulletController(bullet: Bullet with PositionBehaviour with MovementBehaviour
    with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour, bulletView: JFXBulletView)
    extends BulletController(bullet, bulletView)


object JFXBulletController:
    def factory()(context: EntityRepositoryContext[Stage, Pane], bullet: Bullet with PositionBehaviour with MovementBehaviour
        with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour, bulletView: JFXBulletView) =
        new JFXBulletController(bullet, bulletView)
