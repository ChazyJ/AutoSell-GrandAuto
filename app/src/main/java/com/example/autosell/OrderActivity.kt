package com.example.autosell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class OrderActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        nameEditText = findViewById(R.id.edit_text_name)
        emailEditText = findViewById(R.id.edit_text_email)
        phoneEditText = findViewById(R.id.edit_text_phone)
        submitButton = findViewById(R.id.button_submit)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (name.isNotBlank() && email.isNotBlank() && phone.isNotBlank()) {
                // Отправить информацию о заказе, например, через API или сохранить в базе данных

                Toast.makeText(this, "Заказ отправлен", Toast.LENGTH_SHORT).show()

                // Перейти обратно на страницу с объявлениями
                val intent = Intent(this, ItemsActivity::class.java)
                startActivity(intent)
                finish() // Закрыть текущую активность, чтобы пользователь не мог вернуться к ней по кнопке "Назад"
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
