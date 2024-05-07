package com.example.autosell

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        val model: TextView = findViewById(R.id.item_list_model)
        val desc: TextView = findViewById(R.id.item_list_desc)
        val price: TextView = findViewById(R.id.item_list_price)

        carId?.let { id ->
            db.collection("cars")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val item = document.toObject(Item::class.java)

                        item?.let {
                            title.text = it.title
                            model.text = it.model
                            desc.text = it.desc
                            val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(it.price)
                            price.text = "Цена: $formattedPrice"

                            // Загрузка изображения с помощью Picasso из URL-адреса в Firestore
                            Picasso.get().load(it.image).into(image)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Обработка ошибок
                }
        }
    }
}