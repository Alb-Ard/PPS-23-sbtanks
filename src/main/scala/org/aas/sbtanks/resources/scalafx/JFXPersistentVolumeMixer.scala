package org.aas.sbtanks.resources.scalafx

import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.Media
import org.aas.sbtanks.resources.SoundMixerLane
import java.io.File
import java.io.FileWriter
import java.io.FileReader
import java.io.BufferedReader
import java.io.FileNotFoundException

trait JFXPersistentVolumeMixer extends JFXSoundMixer:
    private val DATA_FILE_PATH = "./soundSettings.cfg"
    private val NEWLINE = sys.props("line.separator")

    def saveSettingsToDisk(): this.type =
        try
            val writer = FileWriter(File(DATA_FILE_PATH))
            SoundMixerLane.values.foreach(l => {
                writer.append(l.toString() + "=" + getVolume(l) + NEWLINE)
            })
            writer.flush()
            writer.close()
        catch
            case ex => {
                System.err.println("Could not save audio settings: " + ex.getMessage())
                ex.printStackTrace()
            }
        this
    
    def loadSettingsFromDisk(): this.type =
        try
            val file = File(DATA_FILE_PATH)
            file.exists() match
                case true =>             
                    val reader = BufferedReader(FileReader(file))
                    reader.lines().map(l => l.split('='))
                        .map(a => a(0) -> a(1))
                        .map((l, v) => SoundMixerLane.valueOf(l) -> v.toDouble)
                        .forEach((l, v) => setVolume(l, v))
                    reader.close()
                case _ => ()
        catch
            case fnf: FileNotFoundException => System.out.println("Audio settings not found")
            case ex => {
                System.err.println("Could not save audio settings: " + ex.getMessage())
                ex.printStackTrace()
            }
        this