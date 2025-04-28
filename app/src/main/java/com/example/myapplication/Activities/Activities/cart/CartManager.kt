package com.example.myapplication.Activities.Activities.cart

object CartManager {
    val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        val existingItem = cartItems.find { it.productId == item.productId }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
    }

    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun increaseQuantity(item: CartItem) {
        item.quantity++
    }

    fun decreaseQuantity(item: CartItem) {
        if (item.quantity > 1) {
            item.quantity--
        }
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.finalPrice * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}



