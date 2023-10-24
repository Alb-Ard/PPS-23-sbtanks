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
        this

    override def unchain(last: PowerUp[E]): PowerUpChain[E] =
        val validBindings = powerUpBindings.getOrElse(last, Seq.empty)

        validBindings.foreach(e => e.consumer(
            last.revert(e.supplier()))
        )
        entities = entities.filterNot(b => validBindings.exists(_.supplier() == b.supplier()))

        super.unchain(last)
        this




object PowerUpChain extends App:

    extension (powerUp: PowerUp[Tank])
        def +(next: PowerUp[Tank]): PowerUpChain[Tank] = PowerUpChain(Seq(powerUp, next))


    val binder = PowerUpChainBinder[Tank]

    val tank1 = BasicTank()
    val tank2= BasicTank()

    val healthSpeedPowerUp: PowerUpChain[Tank] = SpeedUp + HealthUp

    binder.bind(tank1)

    binder.chain(HealthUp)

    println(tank1.tankData)
    println(tank2.tankData)

    binder.bind(tank2)

    binder.chain(SpeedUp)

    println(tank1.tankData)
    println(tank2.tankData)


    binder.unchain(HealthUp)

    println(tank1.tankData)
    println(tank2.tankData)

    //tank1.tankData should be((TankData(defaultHealth + 10, defaultSpeed + 10)))
    //tank2.tankData should be((TankData(defaultHealth, defaultSpeed)))

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
    


