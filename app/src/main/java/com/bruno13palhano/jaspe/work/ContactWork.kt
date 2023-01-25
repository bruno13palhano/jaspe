package com.bruno13palhano.jaspe.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.repository.RepositoryFactory
import com.example.network.service.NetworkFactory

class ContactWork(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params){

    private val contactInfoRepository = RepositoryFactory(context)
        .createContactInfoRepository()
    private val contactInfoNetwork = NetworkFactory().createContactInfoNetwork()

    override suspend fun doWork(): Result {
        contactInfoNetwork.getContactInfo().collect {
            contactInfoRepository.insertContactInfo(it)
        }

        return Result.success()
    }
}