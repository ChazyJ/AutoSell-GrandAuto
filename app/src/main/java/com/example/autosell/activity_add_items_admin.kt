package com.example.autosell

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

// Импорт класса Car
import com.example.autosell.models.Car

class activity_items_admin : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage

    private lateinit var titleEditText: EditText
    private lateinit var modelEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var uploadButton: Button

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("cars")
        storage = FirebaseStorage.getInstance()

        titleEditText = findViewById(R.id.edit_text_title)
        modelEditText = findViewById(R.id.edit_text_model)
        descriptionEditText = findViewById(R.id.edit_text_description)
        priceEditText = findViewById(R.id.edit_text_price)
        imageView = findViewById(R.id.image_view)
        uploadButton = findViewById(R.id.button_upload)

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        uploadButton.setOnClickListener {
            uploadCar()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun uploadCar() {
        val title = titleEditText.text.toString()
        val model = modelEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val price = priceEditText.text.toString()

        if (title.isNotEmpty() && model.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && imageUri != null) {
            val carId = database.push().key
            val storageRef = storage.reference.child("cars/${carId}.jpg")

            storageRef.putFile(imageUri!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val car = Car(carId, title, model, description, price, uri.toString())
                        carId?.let { database.child(it).setValue(car) }
                        Toast.makeText(this, "Автомобиль добавлен", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Пожалуйста, заполните все поля и добавьте изображение", Toast.LENGTH_SHORT).show()
        }
    }
}
