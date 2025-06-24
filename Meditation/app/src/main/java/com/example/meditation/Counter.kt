package com.example.meditation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class CounterActivity : AppCompatActivity() {

    private lateinit var textViewNumber: TextView
    private lateinit var buttonPlus: AppCompatButton
    private lateinit var buttonMinus: AppCompatButton
    private lateinit var buttonGetNumber: AppCompatButton

    private var currentNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        textViewNumber = findViewById(R.id.textViewNumber)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonGetNumber = findViewById(R.id.buttonGetNumber)

        updateNumber()

        buttonPlus.setOnClickListener {
            currentNumber++
            updateNumber()
        }

        buttonMinus.setOnClickListener {
            if (currentNumber > 0) {
                currentNumber--
                updateNumber()
            }
        }

        buttonGetNumber.setOnClickListener {
            val intent = Intent(this, RandomNumberActivity::class.java)
            intent.putExtra("maxNumber", currentNumber)
            startActivity(intent)
        }
    }

    private fun updateNumber() {
        textViewNumber.text = currentNumber.toString()
    }
}
