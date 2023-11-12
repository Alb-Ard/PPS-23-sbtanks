package org.aas.sbtanks.entities.bullet.controller.scalafx

import org.aas.sbtanks.behaviours.{CollisionBehaviour, DamageableBehaviour, DirectionBehaviour, MovementBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.bullet.controller.BulletController
import org.aas.sbtanks.entities.bullet.view.BulletView
import org.aas.sbtanks.entities.bullet.view.scalafx.JFXBulletView
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.stage.Stage

class JFXBulletController(using context: EntityRepositoryContext[Stage])(bullet: Bullet with PositionBehaviour with MovementBehaviour
    with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour, bulletView: JFXBulletView)
    extends BulletController(bullet)


object JFXBulletController:
    def factory()(context: EntityRepositoryContext[Stage], bullet: Bullet with PositionBehaviour with MovementBehaviour
        with DirectionBehaviour with CollisionBehaviour with DamageableBehaviour, bulletView: JFXBulletView) =
        new JFXBulletController(using context)(bullet, bulletView)
