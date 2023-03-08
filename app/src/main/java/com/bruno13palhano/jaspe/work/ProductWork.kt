package com.bruno13palhano.jaspe.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.repository.di.DefaultProductRepository
import com.bruno13palhano.repository.repository.ProductRepository
import com.example.network.service.NetworkFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProductWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @DefaultProductRepository val productRepository: ProductRepository
) : CoroutineWorker(context, params) {

    private val productNetwork = NetworkFactory().createProductNetwork()

    override suspend fun doWork(): Result {
        productNetwork.getProducts(listOf(0, 100)).collect {
            productRepository.insertProducts(it)
        }

        return Result.success()
    }
}