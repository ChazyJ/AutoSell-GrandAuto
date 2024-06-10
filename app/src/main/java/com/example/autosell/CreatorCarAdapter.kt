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
import java.text.DecimalFormat

class AdminCarAdapter(var items: List<Item>, var context: Context) : RecyclerView.Adapter<AdminCarAdapter.MyViewHolder>() {

    private val priceFormat = DecimalFormat("#.##")

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val model: TextView = view.findViewById(R.id.item_list_model)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val viewButton: Button = view.findViewById(R.id.item_list_button_admin)
        val delButton: Button = view.findViewById(R.id.item_list_button_del)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list_admin, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.model.text = "${item.title} ${item.model}" // Combine title and model
        holder.desc.text = item.desc
        holder.price.text = "${priceFormat.format(item.price)}$" // Format price

        // Load image with Picasso
        Picasso.get()
            .load(item.image) // Load from image field in Item
            .placeholder(R.drawable.placeholder_image) // Set placeholder
            .error(R.drawable.ic_logout) // Handle error
            .into(holder.image)

        holder.viewButton.setOnClickListener {
            val intent = Intent(context, CarDetailsActivity_admin::class.java)
            intent.putExtra("car_id", item.id)
            context.startActivity(intent)
        }

        holder.delButton.setOnClickListener {
            // Deletion logic
        }
    }

    // Method to set new data list
    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
}
