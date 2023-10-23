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

    override def apply(entity: E): E =
        powerUps.foldLeft(entity)((acc, powerUp) => powerUp.apply(acc))

    override def revert(entity: E): E =
        powerUps.foldRight(entity)((powerUp, acc) => powerUp.revert(acc))


    def chain(next: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps :+ next)

    def unchain(last: PowerUp[E]): PowerUpChain[E] = PowerUpChain(powerUps.filter(_ != last))





trait DualBinder[E]:
    case class EntityBinding(supplier: () => E, consumer: E => Unit)

    private object EntityBinding:
        def apply(e: E): EntityBinding = new EntityBinding(() => e, _ => {})

    protected var entities = Seq.empty[EntityBinding]

    def bind(entity: E): Unit =
        entities = entities :+ EntityBinding(entity)
    def unbind(entity: E): Unit =
        entities = entities.collect:
            case e if e.supplier() != entity => e





class PowerUpChainBinder[E] extends PowerUpChain[E](Seq.empty) with DualBinder[E]:

    override def chain(next: PowerUp[E]): PowerUpChain[E] =
        entities.foreach(e => e.consumer(
            next(e.supplier()))
        )
        super.chain(next)
        this

    override def unchain(last: PowerUp[E]): PowerUpChain[E] =
        entities.foreach(e => e.consumer(
            last.revert(e.supplier()))
        )
        super.unchain(last)
        this




object PowerUpChain extends App:

    extension (powerUp: PowerUp[Tank])
        def +(next: PowerUp[Tank]): PowerUpChain[Tank] = PowerUpChain(Seq(powerUp, next))

    val tank = BasicTank()


    /*
    val persChainer = PowerUpChainBinder[Tank]


    val tank = BasicTank()



    persChainer.bind(tank)




    persChainer.chain(HealthUp + SpeedUp)


    persChainer.unchain(HealthUp)


    println(tank.tankData.health)
    println(tank.tankData.speed)

    //val tank2 = BasicTank()

    /*
     val healthUp: PowerUp[Tank] = FuncPowerUp(
        t => {t updateTankData (
            t.tankData.updateHealth(_ + 10)
        ); t},
          t => {t updateTankData (
                t.tankData.updateHealth(_ - 10)
            ); t}
    ) */


    persChainer.bind(tank)


    persChainer.chain(HealthUp).chain(SpeedUp)

    persChainer.unchain(HealthUp)


    //persChainer.unbind(tank2)


    println(tank.tankData.health)
    tank updateTankData {tank.tankData.updateHealth(_ + 10)}
    println(tank.tankData.health)

    persChainer.unbind(tank)

    persChainer.chain(HealthUp)

    //println(tank2.tankData.health)
    println(tank.tankData.health)
    println(tank.tankData.speed)

    */
    


