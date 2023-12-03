package org.aas.sbtanks.physics

enum CollisionLayer:
    case TanksLayer extends CollisionLayer
    case BulletsLayer extends CollisionLayer
    case WallsLayer extends CollisionLayer
    case NonWalkableLayer extends CollisionLayer
    case PowerUpLayer extends CollisionLayer