package com.example.myapplication.Activities.Activities.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Activities.Activities.adapter.CartAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartAdapter = CartAdapter(CartManager.cartItems) {
            updateTotal()
        }

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        updateTotal()

        val buttons = listOf(
            binding.creditCardBtn,
            binding.groceryPointBtn
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                buttons.forEach {
                    it.setBackgroundColor(
                        ContextCompat.getColor(this, R.color.button_normal)
                    )
                    it.setTextColor(ContextCompat.getColor(this, R.color.black)) // restaurar color texto normal si quieres
                }
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.greendark))
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }
    }

    private fun updateTotal() {
        binding.totalPriceTextView.text = "Total:                                               $${
            String.format(
                "%.2f",
                CartManager.getTotalPrice()
            )
        }"
    }

}
