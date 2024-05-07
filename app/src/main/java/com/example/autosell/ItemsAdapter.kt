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
import com.squareup.picasso.Picasso

class ItemsAdapter(var items: List<Item>, var context: Context) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val model: TextView = view.findViewById(R.id.item_list_model) // Добавлено поле для модели
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val button: Button = view.findViewById(R.id.item_list_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.model.text = items[position].model // Установка значения модели
        holder.desc.text = items[position].desc
        holder.price.text = items[position].price.toString()

        // Загрузка изображения с помощью Picasso
        Picasso.get().load(items[position].image).placeholder(R.drawable.placeholder_image).into(holder.image)

        holder.button.setOnClickListener {
            val intent = Intent(context, CarDetailsActivity::class.java)
            intent.putExtra("car_id", items[position].id)
            context.startActivity(intent)
        }
    }

    // Метод для установки нового списка данных
    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}