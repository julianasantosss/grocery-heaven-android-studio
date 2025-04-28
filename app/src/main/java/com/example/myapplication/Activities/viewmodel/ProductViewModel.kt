package com.example.myapplication.Activities.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Activities.data.model.Product
import com.example.myapplication.Activities.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    val allProducts = repository.allProducts

    fun insert(product: Product) = viewModelScope.launch {

        repository.insert(product)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }
}
