package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.behaviours.{CollisionBehaviour, ConstrainedMovementBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.EnemyUtils.*
import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad.computeState

trait AiMovementBehavior:
    this: AiEntity =>


    /*
        TODO:
            1- make DirectionBehavior compatible with EnemyDirection
            2- avoid unecessary side-effects by using decorated data structure instead of Enemy
     */
    private var context = Enemy(EnemyDirection.LeftX, (this.positionX, this.positionY))


    def isMoveAllowed(newPos: (Double, Double)): Boolean =
        this.testMoveRelative(newPos._1, newPos._2)



    private def move(newPos: (Double, Double)): Unit =
        this.moveRelative(newPos._1, newPos._2)

    def compute(): this.type =
        val (newPos, newContext): ((Double, Double), Enemy) = computeState().runAndTranslate(context)
        
        this.directionX



        this.move(newPos._1, newPos._2)

        this






