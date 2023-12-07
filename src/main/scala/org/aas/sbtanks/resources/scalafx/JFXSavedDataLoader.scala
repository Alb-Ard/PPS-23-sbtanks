package org.aas.sbtanks.resources.scalafx

import java.io.{BufferedReader, File, FileNotFoundException, FileReader, FileWriter}

/**
 * This class is used to recover data from an external file that will be used in the game.
 */
class JFXSavedDataLoader:
    private val DATA_FILE_PATH = "./savedData.cfg"
    private val NEWLINE = sys.props("line.separator")

    /**
     * This method saves the highest score and the username into an external file.
     *
     * @param highScore the high score int that will be saved.
     * @param username the username string that will be saved.
     * @return this type.
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
     * This method loads and retrieves saved data from external file.
     *
     * @return this type.
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