package com.bruno13palhano.jaspe.ui.category

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bruno13palhano.jaspe.ui.category.amazon.AmazonCategoryViewModel
import com.bruno13palhano.jaspe.ui.category.amazon.AmazonCategoryViewModelFactory
import com.bruno13palhano.jaspe.ui.category.avon.AvonCategoryViewModel
import com.bruno13palhano.jaspe.ui.category.avon.AvonCategoryViewModelFactory
import com.bruno13palhano.jaspe.ui.category.baby.BabyCategoryViewModel
import com.bruno13palhano.jaspe.ui.category.baby.BabyCategoryViewModelFactory
import com.bruno13palhano.jaspe.ui.category.highlights.HighlightsCategoryViewModel
import com.bruno13palhano.jaspe.ui.category.highlights.HighlightsCategoryViewModelFactory
import com.bruno13palhano.jaspe.ui.category.natura.NaturaCategoryViewModel
import com.bruno13palhano.jaspe.ui.category.natura.NaturaCategoryViewModelFactory
import com.bruno13palhano.jaspe.ui.category.offers.OffersCategoryViewModel
import com.bruno13palhano.jaspe.ui.category.offers.OffersCategoryViewModelFactory
import com.bruno13palhano.repository.RepositoryFactory

class CategoriesViewModelFactory(
    context: Context,
    private val owner: ViewModelStoreOwner
) {
    private val repositoryFactory = RepositoryFactory(context)

    fun createAmazonCategoryViewModel(): AmazonCategoryViewModel {
        val amazonViewModelFactory =
            AmazonCategoryViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, amazonViewModelFactory)[AmazonCategoryViewModel::class.java]
    }

    fun createAvonCategoryViewModel(): AvonCategoryViewModel {
        val avonViewModelFactory =
            AvonCategoryViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, avonViewModelFactory)[AvonCategoryViewModel::class.java]
    }

    fun createNaturaCategoryViewModel(): NaturaCategoryViewModel {
        val naturaViewModelFactory =
            NaturaCategoryViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, naturaViewModelFactory)[NaturaCategoryViewModel::class.java]
    }

    fun createBabyCategoryViewModel(): BabyCategoryViewModel {
        val babyViewModelFactory =
            BabyCategoryViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, babyViewModelFactory)[BabyCategoryViewModel::class.java]
    }

    fun createOffersCategoryViewModel(): OffersCategoryViewModel {
        val offersViewModelFactory =
            OffersCategoryViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, offersViewModelFactory)[OffersCategoryViewModel::class.java]
    }

    fun createHighlightsCategoryViewModel(): HighlightsCategoryViewModel {
        val highlightsViewModel =
            HighlightsCategoryViewModelFactory(repositoryFactory.createProductRepository())
        return ViewModelProvider(owner, highlightsViewModel)[HighlightsCategoryViewModel::class.java]
    }
}