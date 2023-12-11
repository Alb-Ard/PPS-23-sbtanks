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
      * Retrieves the volume for the given audio lane
      *
      * @param lane The lane for which to retrieve the volume
      * @return The volume value, in the range [0, 1]
      */
    def getVolume(lane: SoundMixerLane) =
        volumes(lane)

    /**
      * Plays the given media
      *
      * @param media The media to play
      * @param lane The audio lane to use
      * @return The MediaPlayer used to play the audio
      */
    def play(media: Media, lane: SoundMixerLane): MediaPlayer =
        play(media, lane, _ => ())
    
    /**
      * Plays the given media
      *
      * @param media The media to play
      * @param lane The audio lane to use
      * @param onBeforePlay A method executed before playing the media
      * @return The MediaPlayer used to play the audio
      */
    def play[U](media: Media, lane: SoundMixerLane, onBeforePlay: MediaPlayer => U): MediaPlayer =
        val player = MediaPlayer(media)
        player.volume = getVolume(lane)
        activePlayers = activePlayers.updated(lane, activePlayers(lane) :+ player)
        player.onEndOfMedia = () => {
            activePlayers = activePlayers.updated(lane, activePlayers(lane).filterNot(p => p == player))
            mediaEnded(media)
        }
        onBeforePlay(player)
        player.play()
        player
