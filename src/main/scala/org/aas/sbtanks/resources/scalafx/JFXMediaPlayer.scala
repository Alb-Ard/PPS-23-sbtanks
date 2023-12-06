package org.aas.sbtanks.resources.scalafx

import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.Media
import org.aas.sbtanks.resources.SoundMixerLane
import java.io.File
import java.io.FileWriter
import java.io.FileReader
import java.io.BufferedReader

object JFXMediaPlayer extends JFXPersistentVolumeMixer:
    val MEDIA_START_MUSIC = (getMedia("1 - Track 1.mp3"), SoundMixerLane.Music)

    val GAME_OVER_SFX = (getMedia("GameOver.mp3"), SoundMixerLane.Sound)

    val BULLET_SFX = (getMedia("SFX 12.wav"), SoundMixerLane.Sound)

    /**
      * Loads the medias marked as precachable to speedup reproduction
      */
    def precache(): this.type =
        for m <- Seq(MEDIA_START_MUSIC) do
            val _ = m
        this

    private def getMedia(name: String) =
        Media(ClassLoader.getSystemResource("medias/" + name).toString())

    extension (mixer: JFXSoundMixer)
        def play(mediaData: (Media, SoundMixerLane)) =
            mixer.play(mediaData(0), mediaData(1))

