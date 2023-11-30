package org.aas.sbtanks.enemies.spawn

import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.aas.sbtanks.entities.tank.factories.*
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.enemies.spawn.PositionProvider
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

/**
 * Object responsible for creating enemy tanks based on a character code.
 * It utilizes a mapping from character codes to tank types defined in the `EnemyFactoryUtils` object.
 */
object EnemyFactory:
    import org.aas.sbtanks.enemies.spawn.EnemyFactoryUtils.mapping

    /**
     * Creates an enemy tank based on the specified character type.
     *
     * @param enemyType The character representing the type of enemy tank.
     * @return An instance of the corresponding enemy tank type.
     */
     private def createEnemy(enemyType: Char) =
        mapping(enemyType) match
            case BasicTankData | FastTankData | PowerTankData | ArmorTankData =>
                new EnemyTankBuilder().setTankType(mapping(enemyType)).build()

    /**
     * Creates a list of enemies based on a string of characters and their positions on a game board.
     *
     * @param input  The input string containing characters representing enemy tank types.
     * @param width  The width of the game board.
     * @param height The height of the game board.
     * @return A list of enemies with positions corresponding to the valid characters in the input string.
     */
    def createFromString(input: String, width: Double, height: Double, eachCharged: Int = 4) =
        input.filter(mapping.contains)
            .map(createEnemy)
            .zipWithIndex
            .map:
                case (enemyTank, index) =>
                    enemyTank.setCharged(eachCharged != 0 && (index + 1) % eachCharged == 0)
            .map(PositionProvider(width, height)(_).findFirstFreePosition())
            .map(_.asInstanceOf[ControllableTank])


/**
 * Object containing utility methods and data for the enemy factory.
 */
object EnemyFactoryUtils:

    /**
     * A mapping from character codes to tank types.
     */
    val mapping: Map[Char, TankTypeData] = Map(
        'B' -> BasicTankData,
        'F' -> FastTankData,
        'P' -> PowerTankData,
        'A' -> ArmorTankData
    )


object x extends App:
    println("dsad")