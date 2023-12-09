package org.aas.sbtanks.entities.powerups
import org.aas.sbtanks.entities.powerups.PowerUp.*
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.*
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank

import scala.annotation.targetName
import scala.language.postfixOps

/**
 * A class representing a chain of power-ups that can be applied to entities of type E.
 *
 * @tparam E The type of entities that can be affected by the power-ups.
 * @param powerUps The sequence of power-ups in the chain.
 */
class PowerUpChain[E](powerUps: Seq[PowerUp[E]]) extends PowerUp[E]:

    /**
     * Creates a new power-up chain with the specified power-ups
     *
     * @param powerUps The power-ups to include in the chain.
     * @return A new power-up chain instance.
     */
    def apply(powerUps: PowerUp[E]*): PowerUpChain[E] =
        PowerUpChain(powerUps)

    /**
     * Applies all the the power-ups int the chain to the specified entity.
     *
     * @param entity The entity to which the power-up chain is applied.
     * @tparam A The specific subtype of E to which the power-up chain is applied.
     * @return The modified entity after applying the power-up chain.
     */
    override def apply[A <: E](entity: A): A =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))

    /**
     * Reverts all the the power-ups int the chain to the specified entity.
     *
     * @param entity The entity to which the power-up chain is reversed.
     * @tparam A The specific subtype of E to which the power-up chain is reverse.
     * @return The modified entity after reversing the power-up chain.
     */
    override def revert[A <: E](entity: A): A =
        powerUps.foldRight(entity)((powerUp, acc) => powerUp.revert(acc))

    /**
     * Chains a new powerup to the current sequence chain of  power-up.
     *
     * @param next The  power-up to chain.
     * @return A new power-up chain instance with the added next power-up.
     */
    def chain(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(next combineWith powerUps )

    /**
     * Unchains a new powerup to the current sequence chain of  power-up.
     * If no existing powerup exists in the sequence this method does nothing
     *
     * @param last The  power-up to unchain.
     * @return A new power-up chain instance with the added next power-up.
     */
    def unchain(last: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps.filter(_ != last))





/**
 * A trait representing a dual binder for entities of type E.
 *
 * @tparam E The type of entities that can be bound and unbound.
 */
trait DualBinder[E]:
    /**
     * An internal case class representing the dual binding aa supplier and a consumer of an entity.
     *
     * @param supplier The function supplying the entity.
     * @param consumer The function consuming the entity.
     */
    case class EntityBinding(supplier: () => E, consumer: E => Unit)

    /**
     * Protected companion object for the EntityBinding case class, providing factory metho fo simpler bindings creation.
     */
    protected object EntityBinding:
        def apply(e: E): EntityBinding = new EntityBinding(() => e, _ => {})

    protected var entities = Seq.empty[EntityBinding]

    /**
     * Binds the specified entity by adding is as a corresponding entity binding.
     *
     * @param entity The entity to bind.
     */
    def bind(entity: E): Unit =
        entities = entities :+ EntityBinding(entity)

    /**
     * Unbinds the specified entity by removing it as a corresponding entity binding.
     *
     * @param entity The entity to unbind.
     */
    def unbind(entity: E): Unit =
        entities = entities.collect:
            case e if e.supplier() != entity => e




/**
 * A class representing a binder for a chain of power-ups with dual binding capabilities for entities of type E.
 * Note: Its PowerUpChain capabilities are initialized with a default empty number of power-ups
 *
 * @tparam E The type of entities that can be affected by the power-ups.
 */
class PowerUpChainBinder[E] extends PowerUpChain[E](Seq.empty) with DualBinder[E]:

    private var powerUpBindings: Map[PowerUp[E], Seq[EntityBinding]] = Map.empty

    /**
     * Retrieves the power-ups currently assigned and active in the binder.
     *
     * @return A sequence of power-ups in the chain.
     */
    def getPowerUps: Seq[PowerUp[E]] =
        powerUpBindings.keys.toSeq

    /**
     * Chains a new power-up and updates entity bindings accordingly, it extend its parent chain functionalities
     * It also take care of clearing existing powerup keys with no bindings registered
     *
     * @param next The power-up to chain.
     * @return The current instance of PowerUpChainBinder after the modification.
     */
    override def chain(next: PowerUp[E]): this.type =
        //val currentBindings = entities.map(_.supplier()).map(EntityBinding(_))

        powerUpBindings = powerUpBindings.filter:
            case (_, storedEntities) => storedEntities.nonEmpty

        powerUpBindings += (next -> entities)

        entities.foreach(e => e.consumer(
            next(e.supplier()))
        )


        //super.chain(next)
        this

    /**
     * Unchains a new power-up and updates entity bindings accordingly, it extend its parent chain functionalities
     * 
     *
     * @param next The power-up to chain.
     * @return The current instance of PowerUpChainBinder after the modification.
     */
    override def unchain(last: PowerUp[E]): this.type =
        val validBindings = powerUpBindings.getOrElse(last, Seq.empty)

        validBindings.foreach(e => e.consumer(
            last.revert(e.supplier()))
        )

        //entities = entities.filterNot(b => validBindings.exists(_.supplier() == b.supplier()))

        powerUpBindings -= last

        //super.unchain(last)
        this




object PowerUpChain extends App:

    extension [E](powerUp: PowerUp[E])
        def +(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(Seq(powerUp, next))







