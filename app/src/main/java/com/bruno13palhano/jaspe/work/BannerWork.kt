package com.bruno13palhano.jaspe.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.repository.di.DefaultBannerRepository
import com.bruno13palhano.repository.repository.BannerRepository
import com.example.network.DefaultBannerNetwork
import com.example.network.service.banner.BannerNetwork
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BannerWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @DefaultBannerRepository val bannerRepository: BannerRepository,
    @DefaultBannerNetwork val bannerNetwork: BannerNetwork
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        bannerNetwork.getBanners().collect {
            bannerRepository.insertBanners(it)
        }

        return Result.success()
    }
}