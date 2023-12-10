package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUp

/**
  * Interface for an object that gives the player points based on game events
  * 
  * @param points A given instance of a points container to use as a destination
  * @param repository The repository that will be checked for tank destruction
  * @param pickupEvent The event used to check when a pickup is collected
  */
trait PointsGiver[M](using points: PointsContainer)(repository: EntityMvRepositoryContainer[M, ?], pickupEvent: EventSource[Option[PowerUp[Tank]]]):
    repository.modelViewAdded += { (m, _) => Option(m)
        .collect:
            case t: Tank with DamageableBehaviour if !t.isInstanceOf[PlayerTank] => t.destroyed once { s => onEnemyTankDestroyed(t, s) }
    }
    pickupEvent += { p => if p.isDefined then points.addAmount(PointsBonuses.pickupCollected) }

    private def onEnemyTankDestroyed(tank: Tank, source: Any) = Option(source)
        .collect:
            case b: Bullet if b.isPlayerBullet => points.addAmount(PointsBonuses.tankDestroyed(tank))

/**
  * Factory for a PointsGiver object
  */
object PointsGiver:
    /**
      * Creates a new PointsGiver
      *
      * @param points A given instance of a points container to use as a destination
      * @param repository The repository that will be checked for tank destruction
      * @param pickupEvent The event used to check when a pickup is collected
      * @return A new object with the PointsGiver trait
      */
    def create[M](using points: PointsContainer)(repository: EntityMvRepositoryContainer[M, ?], pickupEvent: EventSource[Option[PowerUp[Tank]]]) =
        new Object() with PointsGiver(repository, pickupEvent)