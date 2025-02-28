package com.example.meditation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var avatarButton: AppCompatImageButton
    private lateinit var settingsButton: AppCompatImageButton

    private lateinit var chillMoodButton: AppCompatImageButton
    private lateinit var relaxMoodButton: AppCompatImageButton
    private lateinit var focusMoodButton: AppCompatImageButton
    private lateinit var anxiousMoodButton: AppCompatImageButton

    private lateinit var meditationBlockButton: AppCompatImageButton
    private lateinit var cardioBlockButton: AppCompatImageButton

    private lateinit var musicButton: AppCompatImageButton
    private lateinit var profileButton: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        avatarButton = findViewById(R.id.avatarButton)
        settingsButton = findViewById(R.id.settingsButton)
        chillMoodButton = findViewById(R.id.chill_mood_button)
        relaxMoodButton = findViewById(R.id.relax_mood_button)
        focusMoodButton = findViewById(R.id.focus_mood_button)
        anxiousMoodButton = findViewById(R.id.anxious_mood_button)
        meditationBlockButton = findViewById(R.id.meditation_block_button)
        cardioBlockButton = findViewById(R.id.cardio_block_button)
        musicButton = findViewById(R.id.musicButton)
        profileButton = findViewById(R.id.profileButton)

        avatarButton.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
        settingsButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }

        chillMoodButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }
        relaxMoodButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }
        focusMoodButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }
        anxiousMoodButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }

        meditationBlockButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }
        cardioBlockButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }

        musicButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }
        profileButton.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
    }
}