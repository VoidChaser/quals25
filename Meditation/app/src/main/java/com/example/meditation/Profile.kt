package com.example.meditation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Profile : AppCompatActivity() {

    private lateinit var avatarImageView: ImageView
    private lateinit var inputName: EditText
    private lateinit var inputNickname: EditText
    private lateinit var inputBirthDate: EditText
    private lateinit var inputEmail: EditText
    private lateinit var saveButton: AppCompatButton

    private val prefsName = "user_profile"

    private var avatarUriString: String? = null

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    // Регистрация launcher для выбора фото из галереи
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            avatarUriString = uri.toString()
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                avatarImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        avatarImageView = findViewById(R.id.avatarImageView)
        inputName = findViewById(R.id.inputName)
        inputNickname = findViewById(R.id.inputNickname)
        inputBirthDate = findViewById(R.id.inputBirthDate)
        inputEmail = findViewById(R.id.inputEmail)
        saveButton = findViewById(R.id.saveButton)

        loadProfile()

        avatarImageView.setOnClickListener {
            // Запускаем выбор изображения из галереи
            pickImageLauncher.launch("image/*")
        }

        inputBirthDate.setOnClickListener {
            showDatePicker()
        }

        saveButton.setOnClickListener {
            if (validateInputs()) {
                saveProfile()
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        // Если в поле уже есть дата, попробуем распарсить и показать её в picker
        val dateText = inputBirthDate.text.toString()
        if (dateText.isNotEmpty()) {
            try {
                val date = dateFormat.parse(dateText)
                date?.let {
                    calendar.time = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
            val pickedCalendar = Calendar.getInstance()
            pickedCalendar.set(y, m, d)
            inputBirthDate.setText(dateFormat.format(pickedCalendar.time))
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun validateInputs(): Boolean {
        val name = inputName.text.toString().trim()
        val nickname = inputNickname.text.toString().trim()
        val email = inputEmail.text.toString().trim()

        if (name.isEmpty()) {
            inputName.error = "Введите имя"
            inputName.requestFocus()
            return false
        }

        if (nickname.isEmpty()) {
            inputNickname.error = "Введите ник"
            inputNickname.requestFocus()
            return false
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.error = "Введите корректный email"
            inputEmail.requestFocus()
            return false
        }

        // Можно добавить валидацию даты, если нужно

        return true
    }

    private fun loadProfile() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        inputName.setText(prefs.getString("name", ""))
        inputNickname.setText(prefs.getString("nickname", ""))
        inputBirthDate.setText(prefs.getString("birthdate", ""))
        inputEmail.setText(prefs.getString("email", ""))

        avatarUriString = prefs.getString("avatarUri", null)
        avatarUriString?.let {
            val uri = Uri.parse(it)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                avatarImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveProfile() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE).edit()
        prefs.putString("name", inputName.text.toString().trim())
        prefs.putString("nickname", inputNickname.text.toString().trim())
        prefs.putString("birthdate", inputBirthDate.text.toString())
        prefs.putString("email", inputEmail.text.toString().trim())
        avatarUriString?.let { prefs.putString("avatarUri", it) }
        prefs.apply()
    }
}
