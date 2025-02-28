package com.example.meditation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Onboarding : AppCompatActivity() {
    private lateinit var enterButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler().postDelayed(
            {
                startActivity(Intent(applicationContext, SplashScreen::class.java))
            }, 3000
        )

        enterButton = findViewById(R.id.enterButton)

        enterButton.setOnClickListener {
            startActivity(Intent(this, SplashScreen::class.java))
        }


        // Получаем ссылку на ImageView
        val logoImageView: ImageView = findViewById(R.id.onboarding_logo)

        // Создаем анимации
        val fadeIn = ObjectAnimator.ofFloat(logoImageView, "alpha", 0f, 1f)
        fadeIn.duration = 1000 // Продолжительность в миллисекундах

        val scaleX = ObjectAnimator.ofFloat(logoImageView, "scaleX", 0.8f, 1f)
        val scaleY = ObjectAnimator.ofFloat(logoImageView, "scaleY", 0.8f, 1f)
        scaleX.duration = 1000
        scaleY.duration = 1000

        val translateY = ObjectAnimator.ofFloat(logoImageView, "translationY", -50f, 0f)
        translateY.duration = 1000

        val rotate = ObjectAnimator.ofFloat(logoImageView, "rotation", -5f, 0f)
        rotate.duration = 1000

        // Создаем AnimatorSet для одновременного запуска анимаций
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeIn, scaleX, scaleY, translateY, rotate)
        animatorSet.start()
    }
}