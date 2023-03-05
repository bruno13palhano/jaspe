package com.bruno13palhano.jaspe.dependencies.factories

import com.bruno13palhano.jaspe.ui.product.ProductViewModel
import com.bruno13palhano.repository.external.ContactInfoRepository
import com.bruno13palhano.repository.external.FavoriteProductRepository
import com.bruno13palhano.repository.external.ProductRepository

class ProductViewModelFactory(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val contactInfoRepository: ContactInfoRepository
) : Factory<ProductViewModel> {
    override fun create(): ProductViewModel {
        return ProductViewModel(
            productRepository = productRepository,
            favoriteProductRepository = favoriteProductRepository,
            contactInfoRepository = contactInfoRepository
        )
    }
}