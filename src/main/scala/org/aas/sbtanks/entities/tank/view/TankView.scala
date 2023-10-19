package org.aas.sbtanks.entities.tank.view

trait TankView:
    def move(positionX: Double, positionY: Double): Unit

    def look(rotation: Double): Unit

    def isMoving(value: Boolean): Unit

object TankView:
    extension (tankView: TankView)
        def lookInDirection(x: Double, y: Double): Unit =   
            if x == 0 && y == 0 then
                return
            tankView.look((x, y) match
                case (0, _) if y < 0 => 0
                case (_, 0) if x > 0 => 90
                case (0, _) if y > 0 => 180
                case (_, 0) if x < 0 => 270
                case _ => 0
            )