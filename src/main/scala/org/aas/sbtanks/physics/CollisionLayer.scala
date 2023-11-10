package org.aas.sbtanks.physics

sealed trait CollisionLayer

object CollisionLayer:
    case object TanksLayer extends CollisionLayer
    case object BulletsLayer extends CollisionLayer
    case object WallsLayer extends CollisionLayer
    case object NonWalkableLayer extends CollisionLayer

    val ALL_LAYERS = Seq(TanksLayer, BulletsLayer, WallsLayer, NonWalkableLayer)