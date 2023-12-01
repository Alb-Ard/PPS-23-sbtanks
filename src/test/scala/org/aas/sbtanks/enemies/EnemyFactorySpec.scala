package org.aas.sbtanks.enemies

import org.aas.sbtanks.enemies.spawn.EnemyFactory
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.factories
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.tank.factories.{ArmorTankData, BasicTankData, FastTankData}
import org.aas.sbtanks.level.MockLevelFactory
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder

class EnemyFactorySpec extends AnyFlatSpec with Matchers:

    val WIDTH: Int = 5
    val HEIGHT: Int = 5


    "It" should "be possible to generate a sequence of specific types of enemies from a string of characters" in:
        val sequence: String = "B F A A"

        val enemies = EnemyFactory.createFromString(sequence, WIDTH, HEIGHT)



        enemies should have size 4

        enemies(0).tankData should be(BasicTankData.supplyData)
        enemies(1).tankData should be(FastTankData.supplyData)
        enemies(2).tankData should be(ArmorTankData.supplyData)
        enemies(3).tankData should be(ArmorTankData.supplyData)

    "The Tanks position" should "be assigned accordingly to the Physic state of the level, in random but not in others collision positions" in:
        PhysicsWorld.clearColliders()

        val sequence: String = "B"

        val freePositions: Seq[(Double, Double)] = Seq((1, 2), (3, 2))


        MockLevelFactory((x, y) => EnemyTankBuilder().setPosition(x, y).build())
            .createFromString(
                    "UUUUU" +
                    "UUUUU" +
                    "U-U-U" +
                    "UUUUU" +
                    "UUUUU", WIDTH
            )


        val enemies = EnemyFactory.createFromString(sequence, WIDTH, HEIGHT)

        (enemies.head.positionX, enemies.head.positionY) should (be(freePositions(0)) or be(freePositions(1)))








