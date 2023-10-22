package org.aas.sbtanks.common.view

trait DirectionableView:
    def look(rotation: Double): Unit

object DirectionableView:
    extension (view: DirectionableView)
        def lookInDirection(x: Double, y: Double): Unit =   
            if x == 0 && y == 0 then
                return
                  view.look((x, y) match
                  case (0, _) if y < 0 => 0
                  case (_, 0) if x > 0 => 90
                  case (0, _) if y > 0 => 180
                  case (_, 0) if x < 0 => 270
                  case _ => 0
            )