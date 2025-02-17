package com.example.quizapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

/**
 * A background service that plays looping background music.
 *
 * This service ensures continuous music playback while the app is running.
 */
class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    /**
     * Initializes and starts the media player when the service is created.
     *
     * @param intent The intent that started the service.
     * @param flags Additional data about how the service was started.
     * @param startId A unique ID for this service start request.
     * @return START_STICKY ensures the service restarts if killed by the system.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!this::mediaPlayer.isInitialized) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music).apply {
                isLooping = true
                start()
            }
        }
        return START_STICKY
    }

    /**
     * Stops playback and releases media resources when the service is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    /**
     * This service does not support binding.
     *
     * @param intent The binding intent.
     * @return Always returns null since binding is not implemented.
     */
    override fun onBind(intent: Intent?): IBinder? = null
}
