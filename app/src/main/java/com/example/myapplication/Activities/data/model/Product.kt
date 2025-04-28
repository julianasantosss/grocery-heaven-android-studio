package com.example.myapplication.Activities.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "products")

data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val pricePerKg: Double,
    val promoPrice: String,
    val finalPrice: Double,
    val discount: Int,
    val image: String,
    val category: Category
)
enum class Category {
    VEGETABLES,
    MEATS,
    BEVERAGES,
    FRUITS,
    SNACKS,
    BREADS
}
class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(categoryName: String): Category {
        return Category.valueOf(categoryName)
    }
}
