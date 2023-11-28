package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.{EntityMvRepositoryContainer}
import org.aas.sbtanks.entities.repository.context.{EntityRepositoryContext, EntityRepositoryContextAware}
import org.aas.sbtanks.level.scalafx.JFXGameOverView
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.player.PlayerTank
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

abstract class JFXPlayerDeathController[VSlotKey](using context: EntityRepositoryContext[Stage, VSlotKey, Pane])(repository: EntityMvRepositoryContainer[AnyRef, Node], levelSequencer: LevelSequencer[AnyRef, Node], uiSlotKey: VSlotKey)
    extends EntityRepositoryContextAware with Steppable:

    private var playerTank = Option.empty[PlayerTank with DamageableBehaviour]

    repository.modelViewAdded += { (m, _) => onModelCreated(m) }
    repository.modelViewReplaced += { a => onModelCreated(a.model) }
    repository.modelViewRemoved += { (m, _) => m match
        case p: PlayerTank with DamageableBehaviour if playerTank.contains(p) => playerTank = Option.empty
        case _ => ()
    }
    repository.entitiesOfModelType[PlayerTank with DamageableBehaviour].headOption.foreach(onModelCreated)

    override def step(delta: Double) = 
        this

    protected def setupGameoverContext(currentContext: EntityRepositoryContext[Stage, VSlotKey, Pane]): EntityRepositoryContext[Stage, VSlotKey, Pane]

    private def gameover(u: Unit): Unit =
       setupGameoverContext(context).viewSlots.get(uiSlotKey).foreach(c => c.children.add(new JFXGameOverView(levelSequencer)))

    private def onModelCreated(m: AnyRef) = m match
        case p: PlayerTank with DamageableBehaviour =>
            playerTank.foreach(p => p.destroyed -= gameover)
            playerTank = Option(p)
            p.destroyed += gameover
        case _ => ()


