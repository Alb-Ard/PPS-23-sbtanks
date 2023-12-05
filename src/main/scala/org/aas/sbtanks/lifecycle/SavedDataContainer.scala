package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.resources.scalafx.JFXSavedDataLoader

/**
 * Trait used in order to retrieve and keep track of data stored in an external file, in this case, the high score and the username.
 */
trait SavedDataContainer:
    /**
     * An event that is invoked when the high score is changed
     */
    val highScoreChanged = EventSource[(Int)]

    private var highScoreValue = JFXSavedDataLoader().loadSavedDiskData()(1)
    private var usernameText = JFXSavedDataLoader().loadSavedDiskData()(0)

    PointsManager.amountChanged += { p => 
        if(highScore < p)
            increaseHighScore(p)    
    }

    private def increaseHighScore(amount: Int): this.type =
        highScoreValue = amount
        highScoreChanged(highScoreValue)
        saveData()
        this

    /**
     * This def is used to save a new username in the external file.
     * @param username the new username
     * @return
     */
    def setUsername(username: String): this.type =
        usernameText = username
        saveData()
        this

    /**
     * This def is used to reset the current high score that is saved in external file.
     * @return
     */
    def resetHighScore(): this.type =
        highScoreValue = 0
        highScoreChanged(0)
        saveData()
        this

    private def saveData() =
        JFXSavedDataLoader().saveDataToDisk(highScore, username)

    /**
     * This is used to retrieve the currently saved high score value.
     * @return the high score.
     */
    def highScore: Int = highScoreValue

    /**
     * This is used to retrieve the currently saved username.
     * @return the username.
     */
    def username: String = usernameText
