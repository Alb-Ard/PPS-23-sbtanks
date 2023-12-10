package org.aas.sbtanks.enemies

import org.aas.sbtanks.enemies.spawn.EnemyFactory
import org.aas.sbtanks.entities.tank.factories
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.tank.factories.{ArmorTankData, BasicTankData, FastTankData}
import org.aas.sbtanks.levels.MockLevelFactory
import org.aas.sbtanks.physics.PhysicsContainer
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.aas.sbtanks.behaviours.PositionMatchers
import org.aas.sbtanks.entities.tank.structure.Tank
import org.scalatest.matchers.MatchResult
import org.scalatest.matchers.Matcher

import org.aas.sbtanks.enemies.spawn.EnemyFactory.withPosition
import org.aas.sbtanks.physics.PhysicsWorld
import scala.util.Random

trait CollectionMatchers:
    class UniqueElementsMatcher[A](collection: Seq[A]) extends Matcher[A]:
        def apply(left: A) =
        MatchResult(
            collection.count(e => e == left) < 2,
            "Collection has multiple instances of " + left,
            "Collection has one instance of " + left,
        )

    def beUniqueIn[A](collection: Seq[A]) = new UniqueElementsMatcher(collection)

class EnemyFactorySpec extends AnyFlatSpec with Matchers with PositionMatchers with CollectionMatchers:

    val WIDTH: Int = 5
    val HEIGHT: Int = 5
    val EACH_CHARGED = 2

    "An EnemyFactory" should "be able to generate a sequence of specific types of enemies from a string of characters" in:
        given PhysicsContainer = new Object() with PhysicsContainer
        val sequence: String = "b f a a"
        val enemies = EnemyFactory.createFromString(sequence)
            .map(_.build())

        all(enemies) should not be (Option.empty)
        enemies should have size 4
        enemies(0).tankData should be(BasicTankData.supplyData)
        enemies(1).tankData should be(FastTankData.supplyData)
        enemies(2).tankData should be(ArmorTankData.supplyData)
        enemies(3).tankData should be(ArmorTankData.supplyData)

    it should "assign positions accordingly to the Physic state of the level, in random but not in others collision positions" in:
        given PhysicsContainer = new Object() with PhysicsContainer
        given Random = Random(420)
        val sequence: String = "b b"
        val freePositions: Seq[(Double, Double)] = Seq((1, 1), (3, 1))
        MockLevelFactory((x, y) => EnemyTankBuilder().setPosition(x, y).build())
            .createFromString(
                    "UUUUU" +
                    "U-U-U" +
                    "UUUUU" +
                    "UUUUU" +
                    "UUUUU", WIDTH
            )

        var enemies = EnemyFactory.createFromString(sequence)
            .map(_.withPosition(WIDTH, HEIGHT))

        all(enemies) should not be (Option.empty)

        val filteredEnemies = enemies.map(x => x.get)

        val finalPositions = filteredEnemies.map(e => (e.positionX, e.positionY))

        all(filteredEnemies) should (have (position (freePositions(0))) or have (position(freePositions(1))))
        all(finalPositions) should beUniqueIn (finalPositions)




    it should "spawn a special charged tank every n tanks" in :
        given PhysicsContainer = new Object() with PhysicsContainer()
        val sequence: String = "bbbbbbbb"
        val enemies = EnemyFactory.createFromString(sequence,EACH_CHARGED)
            .map(_.build())


        val chargedTanks = enemies.zipWithIndex
            .filter:
                case (_, index) => EACH_CHARGED != 0 && (index + 1) % EACH_CHARGED == 0

        chargedTanks should have size (if (EACH_CHARGED > 0) enemies.size / EACH_CHARGED else 0)


        chargedTanks.foreach:
            case (tank: Tank, _) =>
                tank.isCharged should be(true)



        val normalTanks = enemies.filterNot(chargedTanks.map(_(0)).contains)

        normalTanks.foreach:
            case tank: Tank =>
                tank.isCharged should be(false)

        normalTanks should have size (enemies.size - chargedTanks.size)











