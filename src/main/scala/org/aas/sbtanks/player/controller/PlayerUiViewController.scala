package org.aas.sbtanks.player.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.player.view.ui.PlayerSidebarView
import scala.reflect.ClassTag
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

class PlayerUiViewController[RM >: Tank, RV](using modelClassTag: ClassTag[RM], viewClassTag: ClassTag[RV])(entityRepository: EntityMvRepositoryContainer[RM, RV], playerSidebarView: PlayerSidebarView) extends Steppable:
    type ControllablePlayerTank = PlayerTank with ControllableTank
    
    private var playerTank = Option.empty[ControllablePlayerTank]

    entityRepository.modelViewAdded += { (m, _) => onModelCreated(m) }
    entityRepository.modelViewReplaced += { a => onModelCreated(a.model) }
    entityRepository.modelViewRemoved += { (m, _) => m match
        case p: ControllablePlayerTank if playerTank.map(pp => p == pp).getOrElse(false) => playerTank = Option.empty
        case e: Tank => recalculateEnemyCount()
        case _ => ()
     }

    override def step(delta: Double) = 
        this

    private def onModelCreated(m: RM) = m match
        case p: ControllablePlayerTank =>
            playerTank.foreach(p => p.damaged -= onPlayerDamaged)
            playerTank = Option(p)
            p.damaged += onPlayerDamaged
        case e: Tank => recalculateEnemyCount()
        case _ => ()

    private def recalculateEnemyCount() = 
        playerSidebarView.remainingEnemiesView.setEnemyCount(entityRepository.entitiesOfModelType[Tank]
            .map(_(0))
            .filter(t => !(t.isInstanceOf[PlayerTank]))
            .size)
    
    private def onPlayerDamaged(amount: Int) = 
        playerTank.foreach(t => playerSidebarView.healthView.setRemainingHealth(t.tankData.health))