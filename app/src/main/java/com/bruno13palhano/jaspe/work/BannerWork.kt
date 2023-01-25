package com.bruno13palhano.jaspe.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.repository.RepositoryFactory
import com.example.network.service.NetworkFactory

class BannerWork(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val bannerRepository = RepositoryFactory(context).createBannerRepository()
    private val bannerNetwork = NetworkFactory().createBannerNetwork()

    override suspend fun doWork(): Result {
        bannerNetwork.getBanners().collect {
            bannerRepository.insertBanners(it)
        }

        return Result.success()
    }
}