package com.bruno13palhano.repository.di

import android.content.Context
import androidx.room.Room
import com.bruno13palhano.repository.database.JaspeDatabase
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