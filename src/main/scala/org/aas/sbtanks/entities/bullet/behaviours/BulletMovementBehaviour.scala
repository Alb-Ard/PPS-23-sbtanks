package org.aas.sbtanks.entities.bullet.behaviours

//import java.util.Calendar
import org.aas.sbtanks.entities.bullet.Bullet
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour

trait BulletMovementBehaviour(bullet: Bullet with SteppedMovementDirectionBehaviour) extends Bullet:

    //val now: Int = Calendar.getInstance().get(Calendar.SECOND)

    def checkCollision: Boolean =


    def move(): Bullet =
      //Thread.sleep(2000);
      bullet.bulletPosition = (bullet.bulletPosition._1 +
                                      (bullet.bulletDirection._1 * bullet.speed),
                                      bullet.bulletPosition._2 +
                                      (bullet.bulletDirection._2 * bullet.speed))
      bullet