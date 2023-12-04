package org.aas.sbtanks.common.view

import _root_.scalafx.scene.Node
import _root_.scalafx.application.Platform

trait JFXDirectionableView extends DirectionableView:
    this: Node =>
    def look(rotation: Double) = Platform.runLater {
        rotate = rotation
    }