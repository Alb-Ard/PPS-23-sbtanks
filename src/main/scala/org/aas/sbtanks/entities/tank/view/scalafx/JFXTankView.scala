package org.aas.sbtanks.entities.tank.view.scalafx

import org.aas.sbtanks.entities.tank.view.TankView

import scalafx.scene.image.ImageView
import scalafx.Includes
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.delegate.PositionDelegate
import scalafx.delegate.SFXDelegate
import scalafx.animation.AnimationTimer

class JFXTankView(private val images: Seq[Image], animationSpeed: Double = 1) extends ImageView(images(0))
    with TankView
    with Includes:

    private val movingAnimation = AnimationTimer { now =>
        image = images(((now / 1000D / 1000D / 1000D) * animationSpeed % 2D).asInstanceOf[Int])
    }

    override def look(rotation: Double): Unit = 
        rotate = rotation

    override def move(positionX: Double, positionY: Double): Unit =
        x = positionX
        y = positionY
    
    override def isMoving(value: Boolean): Unit = 
        value match
            case true => movingAnimation.start()
            case false => movingAnimation.stop()
        