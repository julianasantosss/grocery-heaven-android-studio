package com.example.myapplication.Activities.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.Activities.data.dao.ProductDao
import com.example.myapplication.Activities.data.model.Product

class ProductRepository(private val dao: ProductDao) {

    val allProducts: LiveData<List<Product>> = dao.getAllProducts()

    suspend fun insert(product: Product) {
        dao.insertProduct(product)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}
