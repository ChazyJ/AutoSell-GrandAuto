package com.example.autosell

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val switchNotifications: Switch = findViewById(R.id.switch_notifications)

        // Установить начальное состояние переключателя уведомлений
        switchNotifications.isChecked = sharedPreferences.getBoolean("notifications", true)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableNotifications()
                sharedPreferences.edit().putBoolean("notifications", true).apply()
            } else {
                disableNotifications()
                sharedPreferences.edit().putBoolean("notifications", false).apply()
            }
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_settings

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    true
                }
                R.id.nav_personal -> {
                    val intent = Intent(this, PersonalActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    true
                }
                R.id.nav_board -> {
                    val intent = Intent(this, ItemsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> true // Текущая активность
                R.id.nav_logout -> {
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun enableNotifications() {
        // Включить уведомления
    }

    private fun disableNotifications() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}
