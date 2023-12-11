package org.aas.sbtanks.levels

import org.aas.sbtanks.level.LevelLoader
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class LevelLoaderTest extends AnyFlatSpec with Matchers:

    val loader = LevelLoader()

    "A loader" should "be able to read a file and give the level layout" in {
        loader.getLevelSeq(1).head._1 should be ("UUUUUUUUUUU" +
                                                        "U--WWWWW--U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "USW-S-S-WSU" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-WWW-W-U" +
                                                        "U-P-WBW---U" +
                                                        "UUUUUUUUUUU")
    }

    it should "be able to return a list of enemies" in {
        loader.getLevelSeq(1).head._3 should be ("bbbbbbbbbbbbfffbbfba")
    }

    it should "be abe to read multiple files" in {
        loader.getLevelSeq(6).length should be (6)
        loader.getLevelSeq(6).head._1 should be("UUUUUUUUUUU" +
                                                        "U--WWWWW--U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "USW-S-S-WSU" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-W-W-W-U" +
                                                        "U-W-WWW-W-U" +
                                                        "U-P-WBW---U" +
                                                        "UUUUUUUUUUU")
        loader.getLevelSeq(6)(1)._1 should be ("UUUUUUUUUUU" +
                                                        "U---W-W---U" +
                                                        "UTW-W-W-WTU" +
                                                        "UTT-W-W-TTU" +
                                                        "U-W-WTW-W-U" +
                                                        "USSWTTTWSSU" +
                                                        "U-WTTTTTW-U" +
                                                        "U-W-WTW-W-U" +
                                                        "U-W-WWW-W-U" +
                                                        "U-P-WBW---U" +
                                                        "UUUUUUUUUUU")

    }




