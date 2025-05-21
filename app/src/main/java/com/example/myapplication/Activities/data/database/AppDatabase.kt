package com.example.myapplication.Activities.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.Activities.data.dao.ProductDao
import com.example.myapplication.Activities.data.dao.UserDao
import com.example.myapplication.Activities.data.model.CategoryConverter
import com.example.myapplication.Activities.data.model.Product
import com.example.myapplication.Activities.data.model.User

@Database(entities = [Product::class, User::class], version = 1)

@TypeConverters(CategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        // Singleton so it does not create multiple instances
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "product_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
