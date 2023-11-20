package org.aas.sbtanks.enemies

import org.aas.sbtanks.enemies.ai.fsm.AbstractStateModifier
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.{ArmorTank, BasicTank}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StateModifierSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:

    val IntModifier = new AbstractStateModifier[Int] {}
    val TankModifier = new AbstractStateModifier[Tank] {}

    val tank: Tank = BasicTank()

    val InitialValue = 10
    val ModifiedValue = 20
    val AddedValue = 5

    val emptyReturnValue: Unit = ()



    "A state modifier" should "be able to get the current value of the state regardless of the type of membership" in:
        val (returnIntValue, internalIntState) = IntModifier
            .getState
            .runAndTranslate(InitialValue)

        returnIntValue should be(InitialValue)
        internalIntState should be(InitialValue)

        val (returnTankValue, internalTankState) = TankModifier
            .getState
            .runAndTranslate(tank)

        returnTankValue should be (tank)
        internalTankState should be(tank)


    "A state modifier" should "be able to set the current value of the state regardless of the type of membership" in:
        val (returnIntValue, internalIntState) = IntModifier
            .setState(ModifiedValue)
            .runAndTranslate(InitialValue)

        returnIntValue should be(emptyReturnValue)
        internalIntState should be(ModifiedValue)

        val newTank: Tank = ArmorTank()

        val (returnTankValue, internalTankState) = TankModifier
            .setState(newTank)
            .runAndTranslate(tank)

        returnTankValue should be(emptyReturnValue)
        internalTankState should be(newTank)

    "A state modifier" should "be able to modify the current value of the state regardless of the type of membership" in:
        val (returnIntValue, internalIntState) = IntModifier
            .modify(_ + AddedValue)
            .runAndTranslate(InitialValue)

        returnIntValue should be(emptyReturnValue)
        internalIntState should be(InitialValue + AddedValue)


    "A state modifier" should "be able to access its own parameters" in:
        val (returnTankValue, internalTankState) = TankModifier
            .gets(_.tankData)
            .runAndTranslate(tank)

        returnTankValue should be(tank.tankData)
        internalTankState should be(tank)





