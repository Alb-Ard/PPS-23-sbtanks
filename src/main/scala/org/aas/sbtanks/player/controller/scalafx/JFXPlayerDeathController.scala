package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.Main.{INTERFACE_SCALE, windowSize}
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.context.{EntityRepositoryContext, EntityRepositoryContextAware}
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.lifecycle.scalafx.JFXGameBootstrapper
import org.aas.sbtanks.lifecycle.view.scalafx.JFXGameOverView
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

    protected def restart(currentContext: EntityRepositoryContext[Stage, VSlotKey, Pane]): JFXGameBootstrapper
    private def gameover(u: Unit): Unit =
        val gameOverView = new JFXGameOverView(levelSequencer, INTERFACE_SCALE, windowSize)
        gameOverView.retryRequested += {_ => restart(context)}
        gameOverView.exitRequested += {_ => System.exit(0)}
        setupGameoverContext(context).viewSlots.get(uiSlotKey).foreach(c => c.children.add(gameOverView))

    private def onModelCreated(m: AnyRef) = m match
        case p: PlayerTank with DamageableBehaviour =>
            playerTank.foreach(p => p.destroyed -= gameover)
            playerTank = Option(p)
            p.destroyed += gameover
        case _ => ()


