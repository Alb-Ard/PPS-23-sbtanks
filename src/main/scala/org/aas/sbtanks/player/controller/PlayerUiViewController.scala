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
        case e: Tank if !e.isInstanceOf[PlayerTank] => playerSidebarView.remainingEnemiesView.enemyDefeated()
        case _ => ()
     }

    override def step(delta: Double) = 
        this

    def setEnemyCount(amount: Int) =
        playerSidebarView.remainingEnemiesView.setEnemyCount(amount)

    def setCompletedLevelCount(count: Int) =
        playerSidebarView.levelNumberView.setLevelNumber(count + 1)

    private def onModelCreated(m: RM) =
        m match
            case p: ControllablePlayerTank =>
                playerTank.foreach(p => p.damaged -= onPlayerDamaged)
                playerTank = Option(p)
                p.damaged += onPlayerDamaged
                playerSidebarView.healthView.setRemainingHealth(p.tankData.health)
            case _ => ()
    
    private def onPlayerDamaged(amount: Int) = 
        playerTank.foreach(t => playerSidebarView.healthView.setRemainingHealth(t.tankData.health))