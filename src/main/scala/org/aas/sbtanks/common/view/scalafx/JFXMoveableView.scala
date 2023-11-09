package org.aas.sbtanks.common.view

import _root_.scalafx.delegate.PositionDelegate
import _root_.scalafx.application.Platform

trait JFXMoveableView[J <: PositionDelegate[?]] extends MoveableView:
    this: J =>
    def move(x: Double, y: Double) = Platform.runLater {
        this.x = x
        this.y = y
    }