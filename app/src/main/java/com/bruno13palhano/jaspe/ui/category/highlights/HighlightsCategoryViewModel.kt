package com.bruno13palhano.jaspe.ui.category.highlights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.model.Product
import com.bruno13palhano.repository.external.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HighlightsCategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productLastSeen = MutableStateFlow<List<Product>>(emptyList())
    val productLastSeen  = _productLastSeen.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.getProductLastSeen(0, 20).collect {
                _productLastSeen.value = it
            }
        }
    }
}