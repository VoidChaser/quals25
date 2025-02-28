package com.example.meditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit

class AudioPlayerActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var textViewCurrentTime: TextView
    private lateinit var textViewTotalTime: TextView
    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonStop: Button
    private var handler = Handler()
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        seekBar = findViewById(R.id.seekBar)
        textViewCurrentTime = findViewById(R.id.textViewCurrentTime)
        textViewTotalTime = findViewById(R.id.textViewTotalTime)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)
        buttonStop = findViewById(R.id.buttonStop)

        // Задаём текст кнопок на русском
        buttonPlay.text = "Воспроизвести"
        buttonPause.text = "Пауза"
        buttonStop.text = "Стоп"

        mediaPlayer = MediaPlayer.create(this, R.raw.audio)

        val duration = mediaPlayer?.duration ?: 0
        textViewTotalTime.text = millisecondsToTimer(duration.toLong())
        seekBar.max = duration

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        buttonPlay.setOnClickListener {
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
                updateSeekBar()
                updateButtonUI()
            }
        }

        buttonPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                updateButtonUI()
            }
        }

        buttonStop.setOnClickListener {
            stopPlayback()
            finish()
        }

        mediaPlayer?.setOnCompletionListener {
            stopPlayback()
            finish()
        }

        updateSeekBar()
        updateButtonUI()
    }

    private fun updateSeekBar() {
        runnable = Runnable {
            val currentPosition = mediaPlayer?.currentPosition ?: 0
            textViewCurrentTime.text = millisecondsToTimer(currentPosition.toLong())
            seekBar.progress = currentPosition
            handler.postDelayed(runnable!!, 1000)
        }
        handler.postDelayed(runnable!!, 1000)
    }

    private fun millisecondsToTimer(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1)
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateButtonUI() {
        if (mediaPlayer?.isPlaying == true) {
            // Кнопка "Воспроизвести" становится серой
            buttonPlay.isEnabled = false
            buttonPlay.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            // Кнопка "Пауза" становится активной
            buttonPause.isEnabled = true
            buttonPause.setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_light))

        } else {
            // Кнопка "Воспроизвести" становится активной
            buttonPlay.isEnabled = true
            buttonPlay.setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_light))
            // Кнопка "Пауза" становится серой
            buttonPause.isEnabled = false
            buttonPause.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        updateButtonUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayback()
        handler.removeCallbacks(runnable!!)
    }

    private fun stopPlayback() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}