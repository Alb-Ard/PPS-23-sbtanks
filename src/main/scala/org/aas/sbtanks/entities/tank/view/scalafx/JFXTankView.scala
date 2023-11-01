package org.aas.sbtanks.entities.tank.view.scalafx

import org.aas.sbtanks.entities.tank.view.TankView

import scalafx.scene.image.ImageView
import scalafx.Includes
import scalafx.scene.Node
import scalafx.scene.image.Image
import scalafx.delegate.PositionDelegate
import scalafx.delegate.SFXDelegate
import scalafx.animation.AnimationTimer
import org.aas.sbtanks.common.view.JFXMoveableView
import org.aas.sbtanks.common.view.JFXDirectionableView
import org.aas.sbtanks.common.view.scalafx.JFXImageViewAnimator

class JFXTankView(private val images: Seq[Seq[Image]], animationSpeed: Double = 1) extends ImageView(images.applyOrElse(0, _ => Seq.empty).applyOrElse(0, _ => null))
    with TankView
    with Includes
    with JFXMoveableView
    with JFXDirectionableView
    with JFXImageViewAnimator(images(0), animationSpeed):
    
    private val rotationAngleStep = 360D / images.size

    override def isMoving(value: Boolean): Unit = 
        value match
            case true => startAnimation()
            case false => stopAnimation()
    
    override def look(rotation: Double): Unit = 
        setImages(images(((rotation / rotationAngleStep) % images.size).asInstanceOf[Int]))
    
    private def angleInRange(angle: Double, min: Double, max: Double) =
        angle > min && angle < max