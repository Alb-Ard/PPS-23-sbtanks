package org.aas.sbtanks.resources.scalafx

import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.Media
import org.aas.sbtanks.resources.SoundMixerLane
import org.aas.sbtanks.event.EventSource

/**
  * A trait for objects that will play audio files
  */
trait JFXSoundMixer:
    val mediaEnded = EventSource[Media]

    private var volumes = SoundMixerLane.values.map(l => l -> 1D).toMap
    private var activePlayers = SoundMixerLane.values.map(l => l -> Seq.empty[MediaPlayer]).toMap

    /**
      * Sets the volume for the given audio lane
      *
      * @param lane The audio lane
      * @param volume The volume, will be clamped in range [0, 1]
      * @return This mixer
      */
    def setVolume(lane: SoundMixerLane, volume: Double): this.type =
        val clampedVolume = Math.max(0D, Math.min(1D, volume))
        volumes = volumes.updated(lane, clampedVolume)
        activePlayers(lane).foreach(p => p.volume = clampedVolume)
        this

    /**
      * Plays the given media
      *
      * @param media The media to play
      * @param lane The audio lane to use
      * @return The MediaPlayer used to play the audio
      */
    def play(media: Media, lane: SoundMixerLane) =
        val player = MediaPlayer(media)
        player.play()
        activePlayers = activePlayers.updated(lane, activePlayers(lane) :+ player)
        player.onEndOfMedia = () => {
            activePlayers = activePlayers.updated(lane, activePlayers(lane).filterNot(p => p == player))
            mediaEnded(media)
        }
        player
