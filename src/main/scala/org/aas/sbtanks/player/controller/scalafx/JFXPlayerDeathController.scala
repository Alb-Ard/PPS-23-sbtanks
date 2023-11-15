package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.level.scalafx.JFXLevelFactory
import org.aas.sbtanks.level.scalafx.JFXGameOverView

class JFXPlayerDeathController(playerTank: ControllableTank with DamageableBehaviour,gameView: JFXLevelFactory):

    def gameover(): Unit =
        if(playerTank.tankData.health == 0)
            new JFXGameOverView()

