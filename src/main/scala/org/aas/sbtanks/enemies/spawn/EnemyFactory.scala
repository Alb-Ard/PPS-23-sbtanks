package org.aas.sbtanks.enemies.spawn

import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.aas.sbtanks.enemies.spawn.EnemyTypes.*
import org.aas.sbtanks.entities.tank.factories.{ArmorTankData, BasicTankData, FastTankData, PowerTankData}
import org.aas.sbtanks.entities.tank.structure.Tank

/**
 * Object responsible for creating enemy tank builders based on a character code.
 */
object EnemyFactory:
    import org.aas.sbtanks.enemies.spawn.EnemyFactoryUtils.mapping

     def createEnemy(enemyType: Char): EnemyTankBuilder =
        mapping(enemyType) match
            case BasicTank => new EnemyTankBuilder().setTankType(BasicTankData)
            case FastTank => new EnemyTankBuilder().setTankType(FastTankData)
            case PowerTank => new EnemyTankBuilder().setTankType(PowerTankData)
            case ArmorTank => new EnemyTankBuilder().setTankType(ArmorTankData)

/**
 * Object containing utility methods and data for the enemy factory.
 */
object EnemyFactoryUtils:

    val mapping: Map[Char, EnemyTypes] = Map(
        'B' -> BasicTank,
        'F' -> FastTank,
        'P' -> PowerTank,
        'A' -> ArmorTank
    )

