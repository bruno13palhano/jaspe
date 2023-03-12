package com.bruno13palhano.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val shopDani: ShopDaniDispatchers)

enum class ShopDaniDispatchers {
    IO
}