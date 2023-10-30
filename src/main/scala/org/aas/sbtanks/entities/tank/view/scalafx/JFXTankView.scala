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

class JFXTankView(private val images: Seq[Image], animationSpeed: Double = 1) extends ImageView(images.applyOrElse(0, _ => null))
    with TankView
    with Includes
    with JFXMoveableView
    with JFXDirectionableView
    with JFXImageViewAnimator(images, animationSpeed):
    
    override def isMoving(value: Boolean): Unit = 
        value match
            case true => startAnimation()
            case false => stopAnimation()
        