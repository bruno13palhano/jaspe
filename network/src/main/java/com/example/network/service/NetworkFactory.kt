package com.example.network.service

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

class NetworkFactory {

    fun createProductNetwork(): ProductNetwork {
        return ProductNetworkImpl()
    }

    fun createBannerNetwork(): BannerNetwork {
        return BannerNetworkImpl()
    }

    fun createContactInfoNetwork(): ContactInfoNetwork {
        return ContactInfoNetworkImp()
    }

    fun createBlogPostNetwork(): BlogPostNetwork {
        return BlogPostNetworkImpl()
    }

    fun createOfferNotificationNetwork(): NotificationNetwork {
        return NotificationNetworkImpl()
    }
}