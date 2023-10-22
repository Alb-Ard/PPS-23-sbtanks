package org.aas.sbtanks.common.view

import scalafx.scene.Node

trait JFXDirectionableView extends DirectionableView:
    this: Node =>
    def look(rotation: Double) =
        rotate = rotation