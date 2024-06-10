package com.example.autosell

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PersonalActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var textEmail: TextView
    private lateinit var textName: TextView
    private lateinit var textPhone: TextView
    private lateinit var textInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        textEmail = findViewById(R.id.text_email)
        textName = findViewById(R.id.text_name)
        textPhone = findViewById(R.id.text_phone)
        textInfo = findViewById(R.id.text_info)

        val user = mAuth.currentUser

        user?.let { currentUser ->
            val userId = currentUser.uid
            val userEmail = currentUser.email

            // Отображаем email из FirebaseAuth
            textEmail.text = "Email: $userEmail"

            // Загружаем другие данные из Firestore
            database.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        textName.text = "Имя: ${document.getString("name")}"
                        textPhone.text = "Телефон: ${document.getString("phone")}"
                        textInfo.text = "Дополнительная информация: ${document.getString("info")}"
                    }
                }
                .addOnFailureListener {
                    textEmail.text = "Ошибка загрузки данных"
                }
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_personal
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_board -> {
                    val intent = Intent(this, ItemsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_personal -> true
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_logout -> {
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
