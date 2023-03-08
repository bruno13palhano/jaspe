package com.bruno13palhano.jaspe.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.repository.di.DefaultContactInfoRepository
import com.bruno13palhano.repository.repository.ContactInfoRepository
import com.example.network.DefaultContactInfoNetwork
import com.example.network.service.contact.ContactInfoNetwork
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ContactWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @DefaultContactInfoRepository val contactInfoRepository: ContactInfoRepository,
    @DefaultContactInfoNetwork val contactInfoNetwork: ContactInfoNetwork
) : CoroutineWorker(context, params){

    override suspend fun doWork(): Result {
        contactInfoNetwork.getContactInfo().collect {
            contactInfoRepository.insertContactInfo(it)
        }

        return Result.success()
    }
}