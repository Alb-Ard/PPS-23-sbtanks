package org.aas.sbtanks.level

import com.sun.tools.javac.Main

import scala.collection.mutable.ListBuffer
import scala.io.Source.*

class LevelLoader: //da qui leggerò i file txt (guarda le librerie che si occupano di caricare tali file

    def getLevelSeq(amount: Int): (Seq[(String, Int, Int)], Seq[String]) =
        val levels = new ListBuffer[(String, Int, Int)]
        val enemySequencePerLevel = new ListBuffer[String]
        for(n <- Range.inclusive(1,amount))
            levels.addOne(getLevel(n)._1, 11, getLevel(n)._2.size)
            enemySequencePerLevel += getLevel(n)._2
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

