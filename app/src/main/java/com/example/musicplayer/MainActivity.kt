package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var media: MediaPlayer
    private lateinit var playPause: Button
    private lateinit var nextActivity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        playPause = findViewById(R.id.playPauseButton)
        nextActivity = findViewById(R.id.nextActivityButton)

        playPause.setOnClickListener {
            if (media.isPlaying) {
                media.pause()
                playPause.text = "Play"
            } else {
                media.start()
                playPause.text = "Pause"
            }
        }

        nextActivity.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}