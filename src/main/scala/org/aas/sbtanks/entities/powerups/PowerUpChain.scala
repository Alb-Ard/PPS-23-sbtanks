package org.aas.sbtanks.entities.powerups
import org.aas.sbtanks.entities.powerups.PowerUp.*
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.*
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank

import scala.annotation.targetName
import scala.language.postfixOps

class PowerUpChain[E](powerUps: Seq[PowerUp[E]]) extends PowerUp[E]:
    def apply(powerUps: PowerUp[E]*): PowerUpChain[E] =
        PowerUpChain(powerUps)

    override def apply[A <: E](entity: A): A =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))

    override def revert[A <: E](entity: A): A =
        powerUps.foldRight(entity)((powerUp, acc) => powerUp.revert(acc))

    def getPowerUps: Seq[PowerUp[E]] = this.powerUps

    def chain(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(next combineWith powerUps )

    def unchain(last: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps.filter(_ != last))






trait DualBinder[E]:
    case class EntityBinding(supplier: () => E, consumer: E => Unit)

    protected object EntityBinding:
        def apply(e: E): EntityBinding = new EntityBinding(() => e, _ => {})

    protected var entities = Seq.empty[EntityBinding]

    def bind(entity: E): Unit =
        entities = entities :+ EntityBinding(entity)
    def unbind(entity: E): Unit =
        entities = entities.collect:
            case e if e.supplier() != entity => e





class PowerUpChainBinder[E] extends PowerUpChain[E](Seq.empty) with DualBinder[E]:

    private var powerUpBindings: Map[PowerUp[E], Seq[EntityBinding]] = Map.empty

    override def chain(next: PowerUp[E]): PowerUpChain[E] =
        val currentBindings = entities.map(_.supplier()).map(EntityBinding(_))

        powerUpBindings += (next -> currentBindings)

        entities.foreach(e => e.consumer(
            next(e.supplier()))
        )
        super.chain(next)

    override def unchain(last: PowerUp[E]): PowerUpChain[E] =
        val validBindings = powerUpBindings.getOrElse(last, Seq.empty)

        validBindings.foreach(e => e.consumer(
            last.revert(e.supplier()))
        )
        entities = entities.filterNot(b => validBindings.exists(_.supplier() == b.supplier()))

        super.unchain(last)




object PowerUpChain extends App:

    extension [E](powerUp: PowerUp[E])
        def +(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(Seq(powerUp, next))







