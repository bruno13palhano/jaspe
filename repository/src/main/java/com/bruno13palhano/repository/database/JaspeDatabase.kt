package com.bruno13palhano.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bruno13palhano.repository.database.dao.ProductDao
import com.bruno13palhano.repository.database.dao.SearchCacheDao
import com.bruno13palhano.repository.database.dao.*
import com.bruno13palhano.repository.database.dao.BannerDao
import com.bruno13palhano.repository.database.dao.BlogPostDao
import com.bruno13palhano.repository.database.dao.ContactInfoDao
import com.bruno13palhano.repository.database.dao.FavoriteProductDao
import com.bruno13palhano.repository.database.dao.NotificationDao
import com.bruno13palhano.repository.model.*
import com.bruno13palhano.repository.model.BannerRep
import com.bruno13palhano.repository.model.FavoriteProductRep
import com.bruno13palhano.repository.model.ProductRep
import com.bruno13palhano.repository.model.SearchCacheRep

@Database(entities = [
    ProductRep::class,
    BannerRep::class,
    FavoriteProductRep::class,
    SearchCacheRep::class,
    ContactInfoRep::class,
    LastSeenRep::class,
    BlogPostRep::class,
    UserRep::class,
    NotificationRep::class],
    version = 1, exportSchema = false)
internal abstract class JaspeDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val bannerDao: BannerDao
    abstract val favoriteProductDao: FavoriteProductDao
    abstract val searchCacheDao: SearchCacheDao
    abstract val contactInfoDao: ContactInfoDao
    abstract val blogPostDao: BlogPostDao
    abstract val userDao: UserDao
    abstract val notificationDao: NotificationDao
}