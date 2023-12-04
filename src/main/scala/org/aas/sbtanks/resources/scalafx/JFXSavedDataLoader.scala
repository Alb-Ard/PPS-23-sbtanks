package org.aas.sbtanks.resources.scalafx

import java.io.{BufferedReader, File, FileNotFoundException, FileReader, FileWriter}

trait JFXSavedDataLoader:
    private val DATA_FILE_PATH = "./savedData.cfg"
    private val NEWLINE = sys.props("line.separator")

    def saveDataToDisk(highScore: Double, username: String): this.type =
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

    def loadSavedDiskData(): this.type =
        try
            val dataFile = File(DATA_FILE_PATH)
            dataFile.exists() match
                case true =>
                    val dataReader = BufferedReader(FileReader(dataFile))
                    dataReader.lines().map(l => l.split('='))
                case _ => ()
        catch
            case notFound: FileNotFoundException => System.out.println("Data file not found")
            case er => {
                System.err.println("Could not load saved data: " + er.getMessage())
                er.printStackTrace()
            }
        this