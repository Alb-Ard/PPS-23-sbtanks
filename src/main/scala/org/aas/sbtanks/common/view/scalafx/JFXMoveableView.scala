package org.aas.sbtanks.common.view

import _root_.scalafx.delegate.PositionDelegate

trait JFXMoveableView[J <: PositionDelegate[?]] extends MoveableView:
    this: J =>
    def move(x: Double, y: Double) =
        this.x = x
        this.y = y