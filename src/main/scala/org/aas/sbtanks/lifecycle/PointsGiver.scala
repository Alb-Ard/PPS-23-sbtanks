package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.bullet.Bullet

/**
  * Interface for an object that gives the player points based on game events
  */
trait PointsGiver[M, V](repository: EntityMvRepositoryContainer[M, V]):
    repository.modelViewAdded += { (m, _) => m match
        case t: Tank with DamageableBehaviour if !t.isInstanceOf[PlayerTank] => t.destroyed once { s => onEnemyTankDestroyed(t, s) }
    }

    private def onEnemyTankDestroyed(tank: Tank, source: Any) = source match
        case b: Bullet if b.isPlayerBullet => PointsManager.addAmount(PointsBonuses.tankDestroyed(tank))
        case _ => ()
