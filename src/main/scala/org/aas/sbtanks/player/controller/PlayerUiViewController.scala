package org.aas.sbtanks.player.controller

import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.player.view.ui.PlayerSidebarView
import scala.reflect.ClassTag
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext

/**
  * A controller that manages the player sidebar UI view
  *
  * @param modelClassTag Class tag of the repository models
  * @param viewClassTag Class tag of the repository views
  * @param context The repository context on which the sidebar will be added
  * @param entityRepository The entity repository used to fetch the data
  * @param playerSidebarView The sidebar view that will show the data
  */
abstract class PlayerUiViewController[RM >: Tank, RV, CVController, CVSlotKey, CVSlot](using modelClassTag: ClassTag[RM], viewClassTag: ClassTag[RV])
    (using context: EntityRepositoryContext[CVController, CVSlotKey, CVSlot])
    (entityRepository: EntityMvRepositoryContainer[RM, RV], playerSidebarView: PlayerSidebarView, uiKey: CVSlotKey)
    extends EntityRepositoryContextAware[CVController, CVSlotKey, CVSlot] with Steppable:

    type ControllablePlayerTank = PlayerTank with ControllableTank
    
    private var playerTank = Option.empty[ControllablePlayerTank]

    context.changed += { (_, n) => n.get(uiKey).foreach(c => addViewToContext(c)) }
    context.viewSlots.get(uiKey).foreach(c => addViewToContext(c))

    entityRepository.modelViewAdded += { (m, _) => onModelCreated(m) }
    entityRepository.modelViewReplaced += { a => onModelCreated(a.model) }
    entityRepository.modelViewRemoved += { (m, _) => m match
        case p: ControllablePlayerTank if playerTank.contains(p) => playerTank = Option.empty
        case e: Tank if !e.isInstanceOf[PlayerTank] => playerSidebarView.remainingEnemiesView.enemyDefeated()
        case _ => ()
     }

    override def step(delta: Double) = 
        this

    def setEnemyCount(amount: Int) =
        playerSidebarView.remainingEnemiesView.setEnemyCount(amount)

    def setCompletedLevelCount(count: Int) =
        playerSidebarView.levelNumberView.setLevelNumber(count + 1)

    protected def addViewToContext(uiContainer: CVSlot): Unit

    private def onModelCreated(m: RM) =
        m match
            case p: ControllablePlayerTank =>
                playerTank.foreach(p => p.damaged -= onPlayerDamaged)
                playerTank = Option(p)
                p.damaged += onPlayerDamaged
                playerSidebarView.healthView.setRemainingHealth(p.tankData.health)
            case _ => ()
    
    private def onPlayerDamaged(source: Any, amount: Int) = 
        playerTank.foreach(t => playerSidebarView.healthView.setRemainingHealth(t.tankData.health))