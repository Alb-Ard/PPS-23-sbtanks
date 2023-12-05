package org.aas.sbtanks.level

import com.sun.tools.javac.Main

import scala.collection.mutable.ListBuffer
import scala.io.Source.*

/**
 * This class's function is to load one or more levels by taking resources from resource directory and extracting them
 * in such a way that the program's other classes can use them to show the correct layout and sequence of enemies for each level
 */
class LevelLoader: //da qui leggerò i file txt (guarda le librerie che si occupano di caricare tali file
    val LEVEL_SIZE = 11

    /**
     * Reads an amount of files from levels resource package.
     * It uses private method getLevel to read one file at a time, getting both the layout and the sequence of
     * enemies for said level
     *
     * @param amount The amount of files it must read
     * @return A tuple, where the first element is a Seq of all the level layouts, while the second element is a Seq of all enemies for each level
     */
    def getLevelSeq(amount: Int) =
        var levels = Seq.empty[(String, Int, String)]
        for(n <- Range.inclusive(1, amount))
            val level = getLevel(n)
            levels = levels :+ (level(0), LEVEL_SIZE, level(1))
        levels

    /**
     * Reads one level file from levels resource package.
     *
     * @param levelNumber the number corresponding to that level
     * @return A tuple, where the first element is a string that represents the layout of the level, while the second element is a string that indicates the sequence of enemies for that level
     */
    private def getLevel(levelNumber: Int): (String, String) =
        val levelLines: ListBuffer[String] = new ListBuffer[String]()
        val levelIterator = fromResource("levels/level"+levelNumber+".txt").getLines()
        while(levelIterator.hasNext)
            levelLines += levelIterator.next()
        (levelLines.take(11).foldLeft("")(_ + _), levelLines.last)