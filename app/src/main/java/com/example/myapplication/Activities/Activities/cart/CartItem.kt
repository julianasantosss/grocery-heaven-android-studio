package com.example.myapplication.Activities.Activities.cart


data class CartItem(
    val productId: Int,
    val name: String,
    val price: Double,
    var quantity: Int = 1,
    val image: String,
    val finalPrice: Double,
    val promoPrice: String
)
