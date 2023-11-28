package org.aas.sbtanks.level

import com.sun.tools.javac.Main

import scala.collection.mutable.ListBuffer
import scala.io.Source.*

class LevelLoader: //da qui leggerò i file txt (guarda le librerie che si occupano di caricare tali file

    /**
     * Reads an amount of files from levels resource package.
     * It uses private method getLevel to read one file at a time, getting both the layout and the sequence of
     * enemies for said level
     *
     * @param amount The amount of files it must read
     * @return A tuple, where the first element is a Seq of all the level layouts, while the second element is a Seq of all enemies for each level
     */
    def getLevelSeq(amount: Int): (Seq[(String, Int, Int)], Seq[String]) =
        val levels = new ListBuffer[(String, Int, Int)]
        val enemySequencePerLevel = new ListBuffer[String]
        for(n <- Range.inclusive(1, amount))
            val level = getLevel(n)
            levels.addOne(level._1, 11, level._2.size)
            enemySequencePerLevel += level._2
        (levels.toSeq, enemySequencePerLevel.toSeq)

    private def getLevel(levelNumber: Int): (String, String) =
        val levelLines: ListBuffer[String] = new ListBuffer[String]()
        val levelIterator = fromResource("levels/level"+levelNumber+".txt").getLines()
        while(levelIterator.hasNext)
            levelLines += levelIterator.next()
        (levelLines.take(11).foldLeft("")(_ + _), levelLines.last)
object TestLevelLoader extends App:

    val levelLoader = new LevelLoader
    println(levelLoader.getLevelSeq(1))

