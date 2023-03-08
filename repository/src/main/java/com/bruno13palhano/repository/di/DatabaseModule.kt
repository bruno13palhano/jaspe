package com.bruno13palhano.repository.di

import android.content.Context
import androidx.room.Room
import com.bruno13palhano.repository.database.JaspeDatabase
import com.bruno13palhano.repository.database.dao.*
import com.bruno13palhano.repository.database.dao.BannerDao
import com.bruno13palhano.repository.database.dao.BlogPostDao
import com.bruno13palhano.repository.database.dao.ContactInfoDao
import com.bruno13palhano.repository.database.dao.FavoriteProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    fun provideBannerDao(database: JaspeDatabase): BannerDao {
        return database.bannerDao
    }

    @Provides
    fun provideBlogPostDao(database: JaspeDatabase): BlogPostDao {
        return database.blogPostDao
    }

    @Provides
    fun provideContactInfoDao(database: JaspeDatabase): ContactInfoDao {
        return database.contactInfoDao
    }

    @Provides
    fun provideFavoriteProductDao(database: JaspeDatabase): FavoriteProductDao {
        return database.favoriteProductDao
    }

    @Provides
    fun provideNotificationDao(database: JaspeDatabase): NotificationDao {
        return database.notificationDao
    }

    @Provides
    fun provideProductDao(database: JaspeDatabase): ProductDao {
        return database.productDao
    }

    @Provides
    fun provideSearchCacheDao(database: JaspeDatabase): SearchCacheDao {
        return database.searchCacheDao
    }

    @Provides
    fun provideUserDao(database: JaspeDatabase): UserDao {
        return database.userDao
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): JaspeDatabase {
        return Room.databaseBuilder(
            appContext,
            JaspeDatabase::class.java,
            "jaspe_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}