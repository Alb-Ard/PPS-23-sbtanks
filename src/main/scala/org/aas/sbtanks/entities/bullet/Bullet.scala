package org.aas.sbtanks.entities.bullet

//possible parameters: speed: Int, direction: (Double, Double), position: (Double, Double)
/*
val bulletSpeed: Int = speed
val bulletDirection: (Double, Double) = direction
var bulletPosition:(Double, Double) = (position._1 + bulletDirection._1, position._2 + bulletDirection._2)
 */
case class Bullet(speed: Int, playerBullet: Boolean):

    val bulletSpeed:Int = speed
    val isPlayerBullet: Boolean = playerBullet
