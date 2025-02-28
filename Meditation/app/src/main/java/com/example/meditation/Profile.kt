package com.example.meditation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile : AppCompatActivity() {
    private lateinit var menuButton: AppCompatImageButton
    private lateinit var exitButton: AppCompatButton
    private lateinit var musicButton: AppCompatImageButton
    private lateinit var midPic1Button: AppCompatImageButton
    private lateinit var midPic2Button: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        menuButton = findViewById(R.id.menuButton)
        exitButton = findViewById(R.id.exitButton)
        musicButton = findViewById(R.id.musicButton)
        midPic1Button = findViewById(R.id.mid_pic_1)
        midPic2Button = findViewById(R.id.mid_pic_2)

        menuButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        exitButton.setOnClickListener {
            startActivity(Intent(this, SplashScreen::class.java))
        }

        musicButton.setOnClickListener {
            startAudio()
        }

        midPic1Button.setOnClickListener {
            startVideo("video1.mp4") // Замените на имя вашего видеофайла в assets
        }

        midPic2Button.setOnClickListener {
            startVideo("video2.mp4") // Замените на имя вашего видеофайла в assets
        }
    }

    private fun startAudio() {
        startActivity(Intent(this, AudioPlayerActivity::class.java))
    }

    private fun startVideo(videoName: String) {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra("videoPath", videoName)
        startActivity(intent)
    }
}