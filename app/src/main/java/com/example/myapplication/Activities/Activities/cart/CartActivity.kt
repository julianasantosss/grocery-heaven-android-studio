package com.example.myapplication.Activities.Activities.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Activities.Activities.MapActivity
import com.example.myapplication.Activities.Activities.adapter.CartAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: ActivityCartBinding
    private val MAP_REQUEST_CODE = 1001

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
                    it.setTextColor(ContextCompat.getColor(this, R.color.black))
                }
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.greendark))
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }

        val searchBar = findViewById<EditText>(R.id.searchBar)
        searchBar.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivityForResult(intent, MAP_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MAP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val addressSelected = data?.getStringExtra("address")
            addressSelected?.let {
                val searchBar = findViewById<EditText>(R.id.searchBar)
                searchBar.setText(it)
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
