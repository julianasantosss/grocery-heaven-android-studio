package com.example.myapplication.Activities.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.Activities.adapter.ProductAdapter
import com.example.myapplication.Activities.Activities.cart.CartActivity
import com.example.myapplication.Activities.Activities.cart.CartItem
import com.example.myapplication.Activities.Activities.cart.CartManager
import com.example.myapplication.Activities.data.database.AppDatabase
import com.example.myapplication.Activities.data.model.Category
import com.example.myapplication.Activities.data.model.Product
import com.example.myapplication.Activities.data.repository.ProductRepository
import com.example.myapplication.Activities.viewmodel.ProductViewModel
import com.example.myapplication.Activities.viewmodel.ProductViewModelFactory
import com.example.myapplication.R

import com.example.myapplication.databinding.ActivityProductsBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(application).productDao()
        val repository = ProductRepository(dao)
        val factory = ProductViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]


                //(insert code)
                        val products = listOf(
                    // VEGETABLES
                    Product(
                        name = "Broccoli",
                        pricePerKg = 4.5,
                        promoPrice = "$8/2kg",
                        discount = 10,
                        image = "broccoli",
                        finalPrice = 8.0,
                        category = Category.VEGETABLES
                    ),
                    Product(
                        name = "Carrot",
                        pricePerKg = 3.0,
                        promoPrice = "$5/2kg",
                        discount = 5,
                        image = "carrot",
                        finalPrice = 5.0,
                        category = Category.VEGETABLES
                    ),
                    Product(
                        name = "Spinach",
                        pricePerKg = 5.0,
                        promoPrice = "$5/1kg",
                        discount = 15,
                        image = "spinach",
                        finalPrice = 5.0,
                        category = Category.VEGETABLES
                    ),

                    // MEATS
                    Product(
                        name = "Salmon Fillet",
                        pricePerKg = 9.5,
                        promoPrice = "$18/2kg",
                        discount = 20,
                        image = "salmon",
                        finalPrice = 18.0,
                        category = Category.MEATS
                    ),
                    Product(
                        name = "Chicken Breast",
                        pricePerKg = 7.0,
                        promoPrice = "$6.5/1kg",
                        discount = 10,
                        image = "chicken",
                        finalPrice = 6.5,
                        category = Category.MEATS
                    ),
                    Product(
                        name = "Beef Steak",
                        pricePerKg = 12.0,
                        promoPrice = "$22/2kg",
                        discount = 25,
                        image = "beef",
                        finalPrice = 22.0,
                        category = Category.MEATS
                    ),

                    // BEVERAGES
                    Product(
                        name = "Orange Juice",
                        pricePerKg = 3.5,
                        promoPrice = "$6/2L",
                        discount = 5,
                        image = "orange_juice",
                        finalPrice = 6.0,
                        category = Category.BEVERAGES
                    ),
                    Product(
                        name = "Apple Cider",
                        pricePerKg = 4.0,
                        promoPrice = "$4/1L",
                        discount = 8,
                        image = "apple_cider",
                        finalPrice = 4.0,
                        category = Category.BEVERAGES
                    ),
                    Product(
                        name = "Water bottle",
                        pricePerKg = 2.5,
                        promoPrice = "$4/2L",
                        discount = 6,
                        image = "water",
                        finalPrice = 4.0,
                        category = Category.BEVERAGES
                    ),

                    // FRUITS
                    Product(
                        name = "Banana",
                        pricePerKg = 2.0,
                        promoPrice = "$3/2kg",
                        discount = 12,
                        image = "banana",
                        finalPrice = 3.0,
                        category = Category.FRUITS
                    ),
                    Product(
                        name = "Strawberry",
                        pricePerKg = 6.0,
                        promoPrice = "$6/1kg",
                        discount = 18,
                        image = "strawberry",
                        finalPrice = 6.0,
                        category = Category.FRUITS
                    ),
                    Product(
                        name = "Apple",
                        pricePerKg = 3.5,
                        promoPrice = "$6/2kg",
                        discount = 10,
                        image = "apple",
                        finalPrice = 6.0,
                        category = Category.FRUITS
                    ),

                    // SNACKS
                    Product(
                        name = "Potato Chips",
                        pricePerKg = 2.5,
                        promoPrice = "$2.5/1bag",
                        discount = 7,
                        image = "chips",
                        finalPrice = 2.5,
                        category = Category.SNACKS
                    ),
                    Product(
                        name = "Chocolate Bar",
                        pricePerKg = 1.5,
                        promoPrice = "$2.5/2units",
                        discount = 5,
                        image = "chocolate",
                        finalPrice = 2.5,
                        category = Category.SNACKS
                    ),
                    Product(
                        name = "Granola Bar",
                        pricePerKg = 2.0,
                        promoPrice = "$3/2units",
                        discount = 10,
                        image = "granola",
                        finalPrice = 3.0,
                        category = Category.SNACKS
                    ),

                    // BREADS
                    Product(
                        name = "Baguette Bread",
                        pricePerKg = 3.0,
                        promoPrice = "$5/2kg",
                        discount = 10,
                        image = "bread",
                        finalPrice = 5.0,
                        category = Category.BREADS
                    ),
                    Product(
                        name = "Whole Wheat Bread",
                        pricePerKg = 3.5,
                        promoPrice = "$7/2kg",
                        discount = 12,
                        image = "wheat_bread",
                        finalPrice = 7.0,
                        category = Category.BREADS
                    ),
                    Product(
                        name = "Croissant",
                        pricePerKg = 4.0,
                        promoPrice = "$4/1kg",
                        discount = 15,
                        image = "croissant",
                        finalPrice = 4.0,
                        category = Category.BREADS
                    )
                )

                products.forEach { product ->
                    viewModel.insert(product)
                }

        /*             //Delete:
                 viewModel.deleteAll()
        */
        adapter = ProductAdapter { product ->
            val cartItem = CartItem(
                productId = product.id,
                name = product.name,
                price = product.pricePerKg,
                image = product.image,
                finalPrice = product.finalPrice,
                promoPrice = product.promoPrice
            )
            CartManager.addItem(cartItem)

            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)

        viewModel.allProducts.observe(this) { products ->
            adapter.submitList(products)
        }

        val buttons = listOf(
            binding.vegetablesBtn,
            binding.meatsBtn,
            binding.beveragesBtn,
            binding.fruitsBtn,
            binding.snacksBtn,
            binding.breadsBtn
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                buttons.forEach {
                    it.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.button_normal
                        )
                    )
                }
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.greendark))
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }

    }
}
