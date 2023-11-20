package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.repository.{EntityMvRepositoryContainer, EntityRepositoryContext, EntityRepositoryContextAware}
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.level.scalafx.JFXLevelFactory
import org.aas.sbtanks.level.scalafx.JFXGameOverView
import org.aas.sbtanks.lifecycle.LevelSequencer
import org.aas.sbtanks.player.PlayerTank
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.stage.Stage

class JFXPlayerDeathController(using context: EntityRepositoryContext[Stage, Pane])(repository: EntityMvRepositoryContainer[AnyRef, Node], levelSequencer: LevelSequencer[AnyRef, Node]) extends EntityRepositoryContextAware with Steppable:

    private var playerTank = Option.empty[PlayerTank with DamageableBehaviour]

    repository.modelViewAdded += { (m, _) => onModelCreated(m) }
    repository.modelViewReplaced += { a => onModelCreated(a.model) }
    repository.modelViewRemoved += { (m, _) =>
        m match
            case p: PlayerTank with DamageableBehaviour if playerTank.map(pp => p == pp).getOrElse(false) => playerTank = Option.empty
            case _ => ()
    }
    def gameover(): Unit =
       new JFXGameOverView(levelSequencer) //qua vi sarà una modifica per aggiungere la V-Box al momento opportuno


    private def onModelCreated(m: AnyRef) =
        m match
            case p: PlayerTank with DamageableBehaviour =>
                playerTank.foreach(p => p.destroyed -= {_ => gameover()})
                playerTank = Option(p)
                p.destroyed += {_ => gameover()}
            case _ => ()

    override def step(delta: Double): JFXPlayerDeathController.this.type = this
