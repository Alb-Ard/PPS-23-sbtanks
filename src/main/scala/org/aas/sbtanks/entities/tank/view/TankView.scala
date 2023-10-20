package org.aas.sbtanks.entities.tank.view

import org.aas.sbtanks.common.view.MoveableView
import org.aas.sbtanks.common.view.DirectionableView

trait TankView extends MoveableView with DirectionableView:
    def isMoving(value: Boolean): Unit