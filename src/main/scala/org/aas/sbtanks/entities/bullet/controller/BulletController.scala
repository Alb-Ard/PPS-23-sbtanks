package org.aas.sbtanks.entities.bullet.controller

//import java.util.Calendar
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.behaviours.{CollisionBehaviour, DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.Collider

trait BulletController(bullet: Bullet with PositionBehaviour with DirectionBehaviour with CollisionBehaviour) extends Bullet:

    private def update(): Unit =
      checkCollision()

    private def checkCollision(colliders: Seq[Collider]): Unit =
      if(bullet.isPlayerBullet)
          colliders.toList match {
            case Bullet | Tank | LevelObstacle =>
          }


      //val now: Int = Calendar.getInstance().get(Calendar.SECOND)
      /*
      def move(): Bullet =
        //Thread.sleep(2000);
        bullet.bulletPosition = (bullet.bulletPosition._1 +
                                        (bullet.bulletDirection._1 * bullet.speed),
                                        bullet.bulletPosition._2 +
                                        (bullet.bulletDirection._2 * bullet.speed))
        bullet

       */