package com.example.network

import com.example.network.service.banner.BannerNetwork
import com.example.network.service.banner.BannerNetworkImpl
import com.example.network.service.blog.BlogPostNetwork
import com.example.network.service.blog.BlogPostNetworkImpl
import com.example.network.service.contact.ContactInfoNetwork
import com.example.network.service.contact.ContactInfoNetworkImp
import com.example.network.service.notification.NotificationNetwork
import com.example.network.service.notification.NotificationNetworkImpl
import com.example.network.service.product.ProductNetwork
import com.example.network.service.product.ProductNetworkImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class DefaultBannerNetwork

@Qualifier
annotation class DefaultBlogPostNetwork

@Qualifier
annotation class DefaultContactInfoNetwork

@Qualifier
annotation class DefaultNotificationNetwork

@Qualifier
annotation class DefaultProductNetwork

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @DefaultBannerNetwork
    @Singleton
    @Binds
    abstract fun bindDefaultBannerNetwork(
        bannerNetwork: BannerNetworkImpl
    ): BannerNetwork

    @DefaultBlogPostNetwork
    @Singleton
    @Binds
    abstract fun bindDefaultBlogPostNetwork(
        blogPostNetwork: BlogPostNetworkImpl
    ): BlogPostNetwork

    @DefaultContactInfoNetwork
    @Singleton
    @Binds
    abstract fun bindDefaultContactInfoNetwork(
        contactInfoNetwork: ContactInfoNetworkImp
    ): ContactInfoNetwork

    @DefaultNotificationNetwork
    @Singleton
    @Binds
    abstract fun bindDefaultNotificationNetwork(
        notificationNetwork: NotificationNetworkImpl
    ): NotificationNetwork

    @DefaultProductNetwork
    @Singleton
    @Binds
    abstract fun bindDefaultProductNetwork(
        productNetwork: ProductNetworkImpl
    ): ProductNetwork
}