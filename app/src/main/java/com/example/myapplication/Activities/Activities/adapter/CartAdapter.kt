package com.example.myapplication.Activities.Activities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.Activities.cart.CartItem
import com.example.myapplication.Activities.Activities.cart.CartManager
import com.example.myapplication.R

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val promoPrice: TextView = itemView.findViewById(R.id.promoPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val addButton: ImageButton = itemView.findViewById(R.id.addButton)
        val removeButton: ImageButton = itemView.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]

        holder.productName.text = item.name
        holder.promoPrice.text = item.promoPrice
        holder.productPrice.text = "$${item.finalPrice * item.quantity}"
        holder.productQuantity.text = item.quantity.toString()

        val context = holder.itemView.context
        val imageId = context.resources.getIdentifier(item.image, "drawable", context.packageName)
        holder.productImage.setImageResource(imageId)

        holder.addButton.setOnClickListener {
            CartManager.increaseQuantity(item)
            notifyItemChanged(position)
            onCartUpdated()
        }

        holder.removeButton.setOnClickListener {
            CartManager.decreaseQuantity(item)
            notifyItemChanged(position)
            onCartUpdated()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}


