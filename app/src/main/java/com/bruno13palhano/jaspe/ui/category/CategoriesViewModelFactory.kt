package com.bruno13palhano.jaspe.ui.category

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bruno13palhano.jaspe.ui.category.blog.BlogViewModel
import com.bruno13palhano.jaspe.ui.category.blog.BlogViewModelFactory
import com.bruno13palhano.jaspe.ui.category.common.CategoriesViewModelFactory
import com.bruno13palhano.jaspe.ui.category.common.CategoriesViewModel
import com.bruno13palhano.jaspe.ui.category.offers.OffersViewModel
import com.bruno13palhano.jaspe.ui.category.offers.OffersViewModelFactory
import com.bruno13palhano.repository.RepositoryFactory

class CategoriesViewModelFactory(
    context: Context,
    private val owner: ViewModelStoreOwner
) {
    private val repositoryFactory = RepositoryFactory(context)

    fun createCategoriesViewModel(): CategoriesViewModel {
        val categoriesViewModelFactory =
            CategoriesViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, categoriesViewModelFactory)[CategoriesViewModel::class.java]
    }

    fun createOffersViewModel(): OffersViewModel {
        val offersViewModelFactory =
            OffersViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, offersViewModelFactory)[OffersViewModel::class.java]
    }

    fun createBlogViewModel(): BlogViewModel {
        val blogViewModelFactory =
            BlogViewModelFactory(repositoryFactory.createBlogRepository())
        return ViewModelProvider(owner, blogViewModelFactory)[BlogViewModel::class.java]
    }
}