package org.aas.sbtanks.resources.scalafx

import java.io.{BufferedReader, File, FileNotFoundException, FileReader, FileWriter}

class JFXSavedDataLoader:
    private val DATA_FILE_PATH = "./savedData.cfg"
    private val NEWLINE = sys.props("line.separator")

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