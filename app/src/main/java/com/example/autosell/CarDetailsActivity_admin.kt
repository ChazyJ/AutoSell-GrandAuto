package com.example.autosell

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.autosell.models.Car
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.Locale

class CarDetailsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        val carId = intent.getStringExtra("car_id")

        val image: ImageView = findViewById(R.id.item_list_image)
        val title: TextView = findViewById(R.id.item_list_title)
        val desc: TextView = findViewById(R.id.item_list_desc)
        val price: TextView = findViewById(R.id.item_list_price)
        val orderButton: Button = findViewById(R.id.item_list_button_order)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_order -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_personal -> {
                    startActivity(Intent(this, PersonalActivity::class.java))
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_board -> {
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    true // Эта активность уже отображается
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_logout -> {
                    startActivity(Intent(this, AuthActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                        overridePendingTransition(0, 0)
                    })
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.nav_board

        carId?.let { id ->
            db.collection("cars")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val item = document.toObject(Car::class.java)

                        item?.let {
                            // Устанавливаем текст как "Название Модель"
                            title.text = "${it.title} ${it.model}"
                            desc.text = it.desc
                            val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(it.price)
                            price.text = "Цена: $formattedPrice"

                            // Загрузка изображения с помощью Picasso из URL-адреса в Firestore
                            Picasso.get().load(it.image).into(image)

                            // Сохраняем title и model в переменные
                            val carTitle = it.title
                            val carModel = it.model

                            // Устанавливаем слушатель на кнопку "Заказать автомобиль"
                            orderButton.setOnClickListener {
                                val intent = Intent(this, OrderActivity::class.java).apply {
                                    putExtra("carTitle", carTitle)
                                    putExtra("carModel", carModel)
                                }
                                startActivity(intent)
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Обработка ошибок
                }
        }
    }
}
