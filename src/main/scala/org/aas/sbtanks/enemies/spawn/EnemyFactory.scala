package org.aas.sbtanks.enemies.spawn

import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.aas.sbtanks.entities.tank.factories.*
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.enemies.spawn.PositionProvider
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.physics.PhysicsContainer
import scala.util.Random

/**
 * Object responsible for creating enemy tanks based on a character code.
 * It utilizes a mapping from character codes to tank types defined in the `EnemyFactoryUtils` object.
 */
object EnemyFactory:
    import org.aas.sbtanks.enemies.spawn.EnemyFactoryUtils.mapping

    /**
     * Creates an enemy tank builder based on the specified character type.
     *
     * @param enemyTypeChar The character representing the type of enemy tank.
     * @return An optional instance of the corresponding enemy tank type if character is valid.
     */
    private def createEnemyBuilder(enemyTypeChar: Char): Option[EnemyTankBuilder] =
        mapping.get(enemyTypeChar).map(new EnemyTankBuilder().setTankType(_))

    /**
     * Creates a list of enemies based on a string of characters and their positions on a game board based on the physic state of the world.
     *
     * @param physic given instance for the PhysicContainer to support position choosing strategies 
     * @param input The input string containing characters representing enemy tank types.
     * @param eachCharged An optional parameter specifying the charging behavior for every Nth enemy. Default is 4, set to 3 for demo.
     * @return A sequence of enemy tank builders only corresponding to the valid characters in the input string.
     */
    def createFromString(using physics: PhysicsContainer)(input: String, eachCharged: Int = 3) =
        input.map(createEnemyBuilder)
            .zipWithIndex
            .map:
                case (Some(enemyTank), index: Int) =>
                    Option(enemyTank.setCharged(eachCharged != 0 && (index + 1) % eachCharged == 0))
                case _ => Option.empty
            .collect:
                case Some(b) => b

    /**
     * Extension method for the EnemyTankBuilder class to set its position on the game board.
     *
     * @return A new Tank whenever its possible to locate one or else an empty Option .
     */
    extension (builder: EnemyTankBuilder)
        def withPosition(using physics: PhysicsContainer, rng: Random)(width: Double, height: Double) =
            PositionProvider(width, height)(builder.collisionMask.toSeq, builder.collisionSizeX, builder.collisionSizeY).findFreePosition() match
                case Some(x, y) =>
                    val tank = builder.build()
                    tank.setPosition(x, y)
                    Option(tank)
                case _ => Option.empty


/**
 * Object containing utility methods and data for the enemy factory.
 */
object EnemyFactoryUtils:

    /**
     * A mapping from character codes to tank types.
     */
    val mapping: Map[Char, TankTypeData] = Map(
        'b' -> BasicTankData,
        'f' -> FastTankData,
        'p' -> PowerTankData,
        'a' -> ArmorTankData
    )


