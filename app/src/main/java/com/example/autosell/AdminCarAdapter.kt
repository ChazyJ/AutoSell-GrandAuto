package com.example.autosell

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autosell.models.Car
import com.squareup.picasso.Picasso

class CarAdapter(private var carList: List<Car>, private val context: Context) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.titleTextView.text = "${car.title} ${car.model}"  // Объединяем название и модель
        holder.descTextView.text = car.desc
        holder.priceTextView.text = car.price.toString() // Установка цены
        Picasso.get().load(car.image).into(holder.imageView)

        holder.viewButton.setOnClickListener {
            val intent = Intent(context, CarDetailsActivity::class.java).apply {
                putExtra("car_id", car.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = carList.size

    fun updateItems(newCarList: List<Car>) {
        carList = newCarList
        notifyDataSetChanged()
    }

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_list_title)
        val descTextView: TextView = itemView.findViewById(R.id.item_list_desc)
        val priceTextView: TextView = itemView.findViewById(R.id.item_list_price)
        val imageView: ImageView = itemView.findViewById(R.id.item_list_image)
        val viewButton: Button = itemView.findViewById(R.id.item_list_button)
    }
}
