package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.media.MediaPlayer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
    private lateinit var media: MediaPlayer
    private val handler = Handler()
    private lateinit var playPause: Button
    private lateinit var currentPosition: TextView
    private lateinit var totalDuration: TextView
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        media = MediaPlayer().apply {
            val assetFileDescriptor = assets.openFd("ddt_rain.mp3")
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            assetFileDescriptor.close()
            prepare()
        }

        currentPosition = findViewById(R.id.currentPositionTextView)
        totalDuration = findViewById(R.id.totalDurationTextView)
        progress = findViewById(R.id.progressBar)

        playPause = findViewById(R.id.playPauseButton)
        playPause.setOnClickListener {
            if (media.isPlaying) {
                media.pause()
                playPause.text = "Play"
            } else {
                media.start()
                playPause.text = "Pause"
            }
        }

        handler.postDelayed(updateProgress, 1000)
    }

    private val updateProgress = object : Runnable {
        override fun run() {
            val currentTime = media.currentPosition
            val duration = media.duration
            val elapsedTime = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong())
                )
            )
            val totalTime = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
                )
            )
            currentPosition.text = elapsedTime
            totalDuration.text = totalTime

            progress.max = duration
            progress.progress = currentTime

            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateProgress)
    }
}