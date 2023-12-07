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
import scalafx.scene.layout.StackPane
import org.aas.sbtanks.common.view.MoveableView
import org.aas.sbtanks.common.view.DirectionableView

class JFXTankView(private val images: Seq[Seq[Image]], private val invincibilityImages: Seq[Image], animationSpeed: Double = 1) extends StackPane
    with TankView
    with Includes
    with MoveableView
    with DirectionableView:

    private class MainImage (images: Seq[Seq[Image]], animationSpeed: Double = 1) extends ImageView(images.applyOrElse(0, _ => Seq.empty).applyOrElse(0, _ => null))
        with Includes
        with JFXMoveableView
        with JFXDirectionableView
        with JFXImageViewAnimator(images(0), animationSpeed):

        private val rotationAngleStep = 360D / images.size

        def isMoving(value: Boolean) = 
            value match
                case true => startAnimation()
                case _ => stopAnimation()
        
        override def look(rotation: Double): Unit = 
            setImages(images(((rotation / rotationAngleStep) % images.size).asInstanceOf[Int]))
        
        private def angleInRange(angle: Double, min: Double, max: Double) =
            angle > min && angle < max

    private class InvincibilityImage(images: Seq[Image], animationSpeed: Double = 1) extends ImageView(images.applyOrElse(0, _ => null))
        with Includes
        with JFXMoveableView
        with JFXImageViewAnimator(images, animationSpeed):

        def isDamageable(value: Boolean) =
            visible = value
            value match
                case true => startAnimation()
                case _ => stopAnimation()

        
    private val mainImage = MainImage(images, animationSpeed)
    private val invincibilityImage = InvincibilityImage(invincibilityImages, animationSpeed)

    override def move(x: Double, y: Double) = 
        mainImage.move(x, y)
        invincibilityImage.move(x, y)

    override def isMoving(value: Boolean) = 
        mainImage.isMoving(value)
        this
    
    override def isDamageable(value: Boolean) =
        invincibilityImage.isDamageable(value)
        this
    
    override def look(rotation: Double): Unit = 
        mainImage.look(rotation)
            