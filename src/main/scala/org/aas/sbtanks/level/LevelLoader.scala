package org.aas.sbtanks.level

import com.sun.tools.javac.Main

import scala.io.Source

class LevelLoader: //da qui leggerò i file txt (guarda le librerie che si occupano di caricare tali file

    private val levelFile = getClass.getResource("levels/level1.txt")

    for (line <- Source.fromURL(levelFile).getLines()) {
        println(line)
    }

//object TestLevelLoader extends App:
