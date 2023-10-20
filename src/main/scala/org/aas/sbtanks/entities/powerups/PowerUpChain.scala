package org.aas.sbtanks.entities.powerups
import org.aas.sbtanks.entities.powerups.PowerUp.*

import scala.collection.mutable
import scala.annotation.tailrec
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.*
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank

class PowerUpChain[E](powerUps: Seq[PowerUp[E]]) extends PowerUp[E]:
    def apply(powerUps: PowerUp[E]*) =
        PowerUpChain(powerUps)

    override def apply(entity: E): E =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))

    def combine(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps :+ next)



class DispatchabePowerUpChain[E] extends PowerUpChain[E](Seq.empty):
    case class EntityBinding(val provider: () => E, val consumer: E => Any)

    private var entities = Seq.empty[EntityBinding]

    def subscribe(entityProvider: () => E, entityConsumer: (entity: E) => Any): Unit =
        entities = entities :+ EntityBinding(entityProvider, entityConsumer)
        //super.apply(entityProvider())

    def unsubscribe(entity: () => E): Unit = 
        entities = entities
            .filterNot(e => e.provider() == entity())

    override def combine(next: PowerUp[E]): PowerUpChain[E] = 
        entities.foreach(e => e.consumer(next(e.provider())))
        super.combine(next)





object PowerUpChain extends App:


    val persChainer = new DispatchabePowerUpChain[Tank]

    val tank = BasicTank()
    persChainer.subscribe(() => tank, t => tank)

    persChainer.combine(new FuncPowerUp(t => {t updateTankData (
            t.tankData.updateHealth(_ + 10)
        ); t}   ))

    

    println(tank.tankData.health)
    tank updateTankData {tank.tankData.updateHealth(_ + 10)}
    println(tank.tankData.health)

    persChainer.unsubscribe(() => tank)

    persChainer.combine(new FuncPowerUp(t => {t updateTankData (
            t.tankData.updateHealth(_ + 10)
        ); t}   ))
    println(tank.tankData.health)

    


    









