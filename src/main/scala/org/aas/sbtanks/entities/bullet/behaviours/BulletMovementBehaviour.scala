package org.aas.sbtanks.entities.bullet.behaviours

//import java.util.Calendar
import org.aas.sbtanks.entities.bullet.Bullet

trait BulletMovementBehaviour(bullet: Bullet):

    //val now: Int = Calendar.getInstance().get(Calendar.SECOND)

    def update(): Bullet =
      Thread.sleep(2000);
      bullet.bulletInitialPosition = (bullet.bulletInitialPosition._1 +
                                      (bullet.bulletDirection._1 * bullet.speed),
                                      bullet.bulletInitialPosition._2 +
                                      (bullet.bulletDirection._2 * bullet.speed))
      bullet