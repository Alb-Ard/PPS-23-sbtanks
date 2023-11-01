package org.aas.sbtanks.level

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.level.LevelFactory.stringEntityFromChar

abstract class LevelFactory[M, V]():
    /**
      * Creates a level from a given level string.
      * The string format is: W = brick wall, S = steel wall, w = water, T = trees, B = player base, 
      * I = ice, U = indestructible wall, P = player (max one per level), - = empty, any other char = ignored
      *
      * @param levelString The level string
      * @param levelEdgeSize The size of the level edge (in tiles)
      * @param entityRepository The repository used as a target for created entities
      * @return A LevelContainer that contains the created level
      */
    def createFromString(levelString: String, levelEdgeSize: Int, entityRepository: EntityMvRepositoryContainer[M, V]) = 
        val levelEntityStrings = levelString.map(stringEntityFromChar).filter(e => e.isDefined)
        for 
            y <- (0 until levelEdgeSize)
            x <- (0 until levelEdgeSize)
        do
            levelEntityStrings(x + y * levelEdgeSize)
                .map(e => createEntityMv(e, x, y))
                .getOrElse(Seq.empty)
                .foreach((m, v) => entityRepository.addModelView(m, Option(v)))
        LevelContainer(entityRepository)

    protected def createEntityMv(entity: LevelFactory.StringEntity, x: Double, y: Double): Seq[(M, V)]

object LevelFactory:
    enum StringEntity(val char: Char):
        case StringBrickWall extends StringEntity('W')
        case StringSteelWall extends StringEntity('S')
        case StringWater extends StringEntity('w')
        case StringTrees extends StringEntity('T')
        case StringBase extends StringEntity('B')
        case StringIce extends StringEntity('I')
        case StringIndestructibleWall extends StringEntity('U')
        case StringPlayer extends StringEntity('P')
        case StringEmpty extends StringEntity('-')

    def stringEntityFromChar(char: Char) =
        StringEntity.values.find(e => e.char == char)