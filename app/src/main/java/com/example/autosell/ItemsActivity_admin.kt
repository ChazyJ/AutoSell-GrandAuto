package com.example.autosell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ItemsActivity : AppCompatActivity() {

    private lateinit var itemsList: RecyclerView
    private lateinit var adapter: ItemsAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        itemsList = findViewById(R.id.itemsList)
        adapter = ItemsAdapter(emptyList(), this)
        itemsList.adapter = adapter
        itemsList.layoutManager = LinearLayoutManager(this)

        // Получаем данные об автомобилях из базы данных
        db.collection("cars")
            .get()
            .addOnSuccessListener { result ->
                val items = mutableListOf<Item>()
                for (document in result) {
                    val id = document.id // Используем идентификатор документа как строку
                    val title = document.getString("title") ?: ""
                    val model = document.getString("Model") ?: ""
                    val desc = document.getString("desc") ?: ""
                    val price = document.getDouble("price") ?: 0.0 // Преобразуем в Double
                    val image = document.getString("image") ?: "" // Получаем ссылку на изображение
                    items.add(Item(id, image, title, model, desc, price))
                }
                adapter.updateItems(items)
            }
            .addOnFailureListener { exception ->
                // Обработка ошибок
            }
    }
}