package com.bruno13palhano.jaspe.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bruno13palhano.jaspe.ui.home.HomeViewModel
import com.bruno13palhano.jaspe.ui.home.HomeViewModelFactory
import com.bruno13palhano.jaspe.ui.product.ProductViewModel
import com.bruno13palhano.jaspe.ui.product.ProductViewModelFactory
import com.bruno13palhano.repository.ProductRepositoryFactory

class ViewModelFactory(
    context: Context,
    private val owner: ViewModelStoreOwner
) {
    private val repositoryFactory = ProductRepositoryFactory(context)

    fun createProductViewModel(): ProductViewModel {
        val productViewModelFactory =
            ProductViewModelFactory(repositoryFactory.createProductRepository())

        return ViewModelProvider(owner, productViewModelFactory)[ProductViewModel::class.java]
    }

    fun createHomeViewModel(): HomeViewModel {
        val homeViewModelFactory =
            HomeViewModelFactory(repositoryFactory.createProductRepository())

        return ViewModelProvider(owner, homeViewModelFactory)[HomeViewModel::class.java]
    }
}