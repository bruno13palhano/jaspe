package com.bruno13palhano.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bruno13palhano.repository.dao.BannerDao
import com.bruno13palhano.repository.dao.ProductDao
import com.bruno13palhano.repository.model.ProductRep

@Database(entities = [ProductRep::class], version = 1, exportSchema = false)
internal abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val bannerDao: BannerDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        ProductDatabase::class.java,
                        "products_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}