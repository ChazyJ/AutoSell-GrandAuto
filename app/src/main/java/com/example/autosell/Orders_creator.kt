package com.example.autosell

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.example.autosell.models.Order

class Orders_admin : AppCompatActivity() {

    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_admin)

        ordersRecyclerView = findViewById(R.id.ordersRecyclerView)
        ordersAdapter = OrdersAdapter()
        ordersRecyclerView.adapter = ordersAdapter
        ordersRecyclerView.layoutManager = LinearLayoutManager(this)

        // Добавление вертикального отступа между элементами RecyclerView
        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(16) // Устанавливаем отступ в пикселях
        ordersRecyclerView.addItemDecoration(verticalSpaceItemDecoration)

        loadOrders()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_order

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_board -> {
                    val intent = Intent(this, ItemsActivity_admin::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_personal -> {
                    val intent = Intent(this, PersonalActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_add -> {
                    val intent = Intent(this, activity_add_items_admin::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_order -> true // Текущая активность
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadOrders() {
        FirebaseFirestore.getInstance().collection("orders")
            .get()
            .addOnSuccessListener { documents ->
                val orders = documents.mapNotNull { doc ->
                    doc.toObject(Order::class.java)
                }
                ordersAdapter.updateOrders(orders)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Ошибка загрузки данных: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
