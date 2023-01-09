package com.bruno13palhano.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bruno13palhano.repository.dao.BannerDao
import com.bruno13palhano.repository.dao.FavoriteProductDao
import com.bruno13palhano.repository.dao.ProductDao
import com.bruno13palhano.repository.model.BannerRep
import com.bruno13palhano.repository.model.ProductRep

@Database(entities = [ProductRep::class, BannerRep::class], version = 1, exportSchema = false)
internal abstract class JaspeDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val bannerDao: BannerDao
    abstract val favoriteProductDao: FavoriteProductDao

    companion object {
        @Volatile
        private var INSTANCE: JaspeDatabase? = null

        fun getInstance(context: Context): JaspeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        JaspeDatabase::class.java,
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