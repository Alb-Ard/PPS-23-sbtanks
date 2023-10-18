package org.aas.sbtanks.entities.tank.view.scalafx

import org.aas.sbtanks.entities.tank.view.TankView

import scalafx.scene.image.ImageView
import scalafx.Includes
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.delegate.PositionDelegate
import scalafx.delegate.SFXDelegate

class JFXTankView(image: Image) extends ImageView(image)
    with TankView
    with Includes:

    override def look(rotation: Double): Unit = 
        rotate = rotation

    override def move(positionX: Double, positionY: Double): Unit =
        x = positionX
        y = positionY