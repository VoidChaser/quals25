
package com.example.meditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.concurrent.TimeUnit

class VideoPlayerActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceView: SurfaceView
    private var mediaPlayer: MediaPlayer? = null
    private var videoPath: String? = null
    private lateinit var seekBar: SeekBar
    private lateinit var textViewCurrentTime: TextView
    private lateinit var textViewTotalTime: TextView
    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonStop: Button
    private lateinit var textViewTitle: TextView
    private var handler = Handler()
    private var runnable: Runnable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        surfaceView = findViewById(R.id.surfaceView)
        seekBar = findViewById(R.id.seekBar)
        textViewCurrentTime = findViewById(R.id.textViewCurrentTime)
        textViewTotalTime = findViewById(R.id.textViewTotalTime)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)
        buttonStop = findViewById(R.id.buttonStop)
        textViewTitle = findViewById(R.id.textViewTitle)

        // Задаём текст кнопок на русском
        buttonPlay.text = "Воспроизвести"
        buttonPause.text = "Пауза"
        buttonStop.text = "Стоп"


        surfaceView.holder.addCallback(this)

        videoPath = intent.getStringExtra("videoPath")
        val videoName = videoPath?.substringBeforeLast(".") ?: "Без названия" // Получаем имя файла без расширения
        textViewTitle.text = "Видеоплеер: $videoName"


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
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDisplay(holder)
            val assetFileDescriptor = assets.openFd(videoPath!!) //videoPath не может быть null
            mediaPlayer?.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            mediaPlayer?.prepare()

            val duration = mediaPlayer?.duration ?: 0
            textViewTotalTime.text = millisecondsToTimer(duration.toLong())
            seekBar.max = duration


            mediaPlayer?.setOnPreparedListener { mp ->
                mp.start()
                updateSeekBar()
                updateButtonUI()
            }
            mediaPlayer?.setOnCompletionListener {
                stopPlayback()
                finish()
            }


        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка открытия видеофайла: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
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

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Not needed
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopPlayback()
    }

    private fun updateButtonUI() {
        if (mediaPlayer?.isPlaying == true) {
            // Кнопка "Воспроизвести" становится серой
            buttonPlay.isEnabled = false
            buttonPlay.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            // Кнопка "Пауза" становится активной
            buttonPause.isEnabled = true
            buttonPause.setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_light))

            buttonPlay.text = "Воспроизвести" // Явное задание текста
            buttonPause.text = "Пауза" // Явное задание текста

        } else {
            // Кнопка "Воспроизвести" становится активной
            buttonPlay.isEnabled = true
            buttonPlay.setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_light))
            // Кнопка "Пауза" становится серой
            buttonPause.isEnabled = false
            buttonPause.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))

            buttonPlay.text = "Воспроизвести" // Явное задание текста
            buttonPause.text = "Пауза" // Явное задание текста
        }
    }


    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        updateButtonUI()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
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