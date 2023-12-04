package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.resources.scalafx.JFXSavedDataLoader

trait SavedDataContainer:
    val highScoreChanged = EventSource[(Int)]

    private var highScoreValue = JFXSavedDataLoader().loadSavedDiskData()(1)
    private var usernameText = JFXSavedDataLoader().loadSavedDiskData()(0)

    PointsManager.amountChanged += { p => 
        if(highScore < p)
            increaseHighScore(p)    
    }

    def increaseHighScore(amount: Int): this.type =
        highScoreValue = amount
        highScoreChanged(highScoreValue)
        saveData()
        this

    def setUsername(username: String): this.type =
        usernameText = username
        //usernameChanged(usernameText)
        saveData()
        this

    def resetHighScore(): this.type =
        highScoreValue = 0
        highScoreChanged(0)
        saveData()
        this

    def saveData() =
        JFXSavedDataLoader().saveDataToDisk(highScore, username)

    def highScore: Int = highScoreValue
    def username: String = usernameText
