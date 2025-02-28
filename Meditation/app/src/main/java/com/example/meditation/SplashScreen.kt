package com.example.meditation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class SplashScreen : AppCompatActivity() {
    private lateinit var enterButton: AppCompatButton
    private lateinit var registrationButton: AppCompatButton
    private lateinit var profileButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        enterButton = findViewById(R.id.enterButton)
        registrationButton = findViewById(R.id.registrationButton)
        profileButton = findViewById(R.id.profileButton)

        enterButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        registrationButton.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))
        }

        profileButton.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
    }
}