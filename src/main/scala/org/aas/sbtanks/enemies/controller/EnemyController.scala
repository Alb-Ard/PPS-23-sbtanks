package org.aas.sbtanks.enemies.controller

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.enemies.ai.*
import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad.computeState
import org.aas.sbtanks.enemies.ai.EnemyUtils.Enemy

class EnemyController(private val enemyTanks: Seq[Tank]) extends Steppable:

    override def step(delta: Double) = ???

    def updateAll(enemies: Seq[Enemy]): (Seq[(Double, Double)], Seq[Enemy]) =
        enemies.map(updateEnemyPosition).unzip
        

    def updateEnemyPosition(enemy: Enemy): ((Double, Double), Enemy) =
        computeState().runAndTranslate(enemy)


