package com.bruno13palhano.repository.di

import com.bruno13palhano.repository.repository.*
import com.bruno13palhano.repository.repository.usercase.*
import com.bruno13palhano.repository.repository.usercase.BannerRepositoryImpl
import com.bruno13palhano.repository.repository.usercase.BlogPostRepositoryImpl
import com.bruno13palhano.repository.repository.usercase.ContactInfoRepositoryImpl
import com.bruno13palhano.repository.repository.usercase.FavoriteProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class DefaultBannerRepository

@Qualifier
annotation class DefaultBlogPostRepository

@Qualifier
annotation class DefaultContactInfoRepository

@Qualifier
annotation class DefaultFavoriteProductRepository

@Qualifier
annotation class DefaultNotificationRepository

@Qualifier
annotation class DefaultProductRepository

@Qualifier
annotation class DefaultSearchCacheRepository

@Qualifier
annotation class DefaultUserRepository

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @DefaultBannerRepository
    @Singleton
    @Binds
    abstract fun bindDefaultBannerRepository(
        repository: BannerRepositoryImpl
    ): BannerRepository

    @DefaultBlogPostRepository
    @Singleton
    @Binds
    abstract fun bindDefaultBlogPostRepository(
        repository: BlogPostRepositoryImpl
    ): BlogPostRepository

    @DefaultContactInfoRepository
    @Singleton
    @Binds
    abstract fun bindDefaultContactInfoRepository(
        repository: ContactInfoRepositoryImpl
    ): ContactInfoRepository

    @DefaultFavoriteProductRepository
    @Singleton
    @Binds
    abstract fun bindDefaultFavoriteProductRepository(
        repository: FavoriteProductRepositoryImpl
    ): FavoriteProductRepository

    @DefaultNotificationRepository
    @Singleton
    @Binds
    abstract fun bindDefaultNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository

    @DefaultProductRepository
    @Singleton
    @Binds
    abstract fun bindDefaultProductRepository(
        repository: ProductRepositoryImpl
    ): ProductRepository

    @DefaultSearchCacheRepository
    @Singleton
    @Binds
    abstract fun bindDefaultSearchCacheRepository(
        repository: SearchCacheRepositoryImpl
    ): SearchCacheRepository

    @DefaultUserRepository
    @Singleton
    @Binds
    abstract fun bindDefaultUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository
}