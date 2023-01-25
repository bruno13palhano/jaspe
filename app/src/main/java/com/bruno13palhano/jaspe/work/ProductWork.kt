package com.bruno13palhano.jaspe.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.repository.RepositoryFactory
import com.example.network.service.NetworkFactory

class ProductWork(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val productRepository = RepositoryFactory(context).createProductRepository()
    private val productNetwork = NetworkFactory().createProductNetwork()

    override suspend fun doWork(): Result {
        productNetwork.getProducts(listOf(0, 100)).collect {
            productRepository.insertProducts(it)
        }

        return Result.success()
    }
}