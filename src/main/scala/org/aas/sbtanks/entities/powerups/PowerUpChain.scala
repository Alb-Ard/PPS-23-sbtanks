package org.aas.sbtanks.entities.powerups
import org.aas.sbtanks.entities.powerups.PowerUp.*


import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.*
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank

class PowerUpChain[E](powerUps: Seq[PowerUp[E]]) extends PowerUp[E]:
    def apply(powerUps: PowerUp[E]*) =
        PowerUpChain(powerUps)

    override def apply(entity: E): E =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))


    def combine(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps :+ next)



class DispatchablePowerUpChain[E] extends PowerUpChain[E](Seq.empty):
    case class EntityBinding(supplier: () => E, consumer: E => Unit)

    private object EntityBinding:
        def apply(e: E): EntityBinding = new EntityBinding(() => e, _ => {})

    private var entities = Seq.empty[EntityBinding]


    def subscribe(entity: E): Unit =
        entities = entities :+ EntityBinding(entity)
    def unsubscribe(entity: E): Unit =
        entities = entities.collect:
            case e if e.supplier() != entity => e


    override def combine(next: PowerUp[E]): PowerUpChain[E] =
        entities.foreach(e => e.consumer(next(e.supplier())))
        super.combine(next)





object PowerUpChain extends App:


    val persChainer = DispatchablePowerUpChain[Tank]

    val tank = BasicTank()
    val tank2 = BasicTank()

    persChainer.subscribe(tank)

    persChainer.combine(FuncPowerUp(t => {t updateTankData (
        t.tankData.updateHealth(_ + 10)
        ); t}   ))



    persChainer.subscribe(tank2)


    println(tank.tankData.health)
    tank updateTankData {tank.tankData.updateHealth(_ + 10)}
    println(tank.tankData.health)

    persChainer.unsubscribe(tank)
    persChainer.unsubscribe(tank2)

    persChainer.combine(FuncPowerUp(t => {t updateTankData (
        t.tankData.updateHealth(_ + 10)
        ); t}   ))

    println(tank2.tankData.health)
    println(tank.tankData.health)

    


