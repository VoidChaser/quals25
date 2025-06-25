package com.example.Kval25

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class RandomNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_number)

        val maxNumber = intent.getIntExtra("maxNumber", 0)
        val randomValue = if (maxNumber > 0) Random.nextInt(0, maxNumber + 1) else 0

        val textViewRange = findViewById<TextView>(R.id.textViewRange)
        val textViewRandomNumber = findViewById<TextView>(R.id.textViewRandomNumber)

        textViewRange.text = "Случайное число между 0 и $maxNumber"
        textViewRandomNumber.text = randomValue.toString()
    }
}
