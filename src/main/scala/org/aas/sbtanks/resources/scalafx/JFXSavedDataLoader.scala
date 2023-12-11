package org.aas.sbtanks.resources.scalafx

import java.io.{BufferedReader, File, FileNotFoundException, FileReader, FileWriter}

/**
 * This class is used load data from external file.
 */
class JFXSavedDataLoader:
    private val DATA_FILE_PATH = "./savedData.cfg"
    private val NEWLINE = sys.props("line.separator")

    /**
     * This method saves the high score and username data from external file.
     *
     * @param highScore The int score to save.
     * @param username The string username to save.
     * @return
     */
    def saveDataToDisk(highScore: Int, username: String): this.type =
        try
            val dataWriter = FileWriter(File(DATA_FILE_PATH))
            dataWriter.append("username=" + username + NEWLINE)
            dataWriter.append("highScore=" + highScore + NEWLINE)
            dataWriter.flush()
            dataWriter.close()
        catch
            case er =>
                System.err.println("Could not save data: " + er.getMessage())
                er.printStackTrace()
        this

    /**
     * This method loads the saved data from an external save file.
     *
     * @return a tuple containing the username and the high score.
     */
    def loadSavedDiskData(): (String, Int) =
        var savedData = ("", 0)
        try
            val dataFile = File(DATA_FILE_PATH)
            dataFile.exists() match
                case true =>
                    val dataReader = BufferedReader(FileReader(dataFile))
                    val dataValues = dataReader.lines().map(l => l.split('=')).map(e => e(1)).toList
                    dataReader.close()
                    savedData = (dataValues.get(0), dataValues.get(1).toInt)
                case _ => ()
        catch
            case notFound: FileNotFoundException => System.out.println("Data file not found")
            case er => {
                System.err.println("Could not load saved data: " + er.getMessage())
                er.printStackTrace()
            }
        savedData