package com.example.meditation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {


    private lateinit var quitButton: AppCompatButton
    private lateinit var mediaButton: AppCompatButton
    private lateinit var profileButton: AppCompatButton
    private lateinit var randnumButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)

            // инициализация кнопок и слушателей
            quitButton = findViewById(R.id.quitButton)
            mediaButton = findViewById(R.id.mediaButton)
            profileButton = findViewById(R.id.profileButton)
            randnumButton = findViewById(R.id.randnumButton)

            quitButton.setOnClickListener {
                finishAndRemoveTask()
            }
            randnumButton.setOnClickListener {
                startActivity(Intent(this, CounterActivity::class.java))
            }
            mediaButton.setOnClickListener {
                startActivity(Intent(this, VideoPlayerActivity::class.java))
            }
            profileButton.setOnClickListener {
                startActivity(Intent(this, Profile::class.java))
            }
        }
    }