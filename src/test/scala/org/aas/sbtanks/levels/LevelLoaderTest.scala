package org.aas.sbtanks.levels

import org.aas.sbtanks.level.LevelLoader
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class LevelLoaderTest extends AnyFlatSpec with Matchers:

    val loader = LevelLoader()

    "A loader" should "be able to read a file and give the level layout" in {
        loader.getLevelSeq(1)._1 should be (Seq(("UUUUUUUUUUU" +
                                                        "U--WWWWW--U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "USW-S-S-WSU" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-WWW-W-U" +
                                                        "U-P-WBW---U" +
                                                        "UUUUUUUUUUU", 11, 10)))
    }

    it should "be able to return a list of enemies" in {
        loader.getLevelSeq(1)._2 should be (Seq("b,b,b,b,b,b,b,b,b,b,b,b,f,f,f,b,b,f,b,a"))
    }

    it should "be abe to read multiple files" in {
        loader.getLevelSeq(6)._1.size should be (6)
        loader.getLevelSeq(6)._2.size should be (6)

    }




