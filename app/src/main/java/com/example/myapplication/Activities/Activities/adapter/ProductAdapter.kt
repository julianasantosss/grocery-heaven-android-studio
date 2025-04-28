package com.example.myapplication.Activities.Activities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.data.model.Product
import com.example.myapplication.R

class ProductAdapter(
        private val onAddToCartClick: (Product) -> Unit
    ) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback()) {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.productName)
        val price = itemView.findViewById<TextView>(R.id.productPrice)
        val promo = itemView.findViewById<TextView>(R.id.productPromo)
        val image = itemView.findViewById<ImageView>(R.id.productImage)
        val addCartButton = itemView.findViewById<ImageButton>(R.id.addCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.price.text = "$${item.finalPrice}/kg"
        holder.promo.text = item.promoPrice

        val imageId = holder.itemView.context.resources.getIdentifier(
            item.image, "drawable", holder.itemView.context.packageName
        )
        holder.image.setImageResource(imageId)
        holder.addCartButton.setOnClickListener {
            onAddToCartClick(item)
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}