package com.bruno13palhano.jaspe.ui.help

import app.cash.turbine.test
import com.bruno13palhano.jaspe.ui.home.MainDispatcherRule
import com.bruno13palhano.jaspe.ui.ModelFactory.makeContactInfo
import com.bruno13palhano.model.ContactInfo
import com.bruno13palhano.repository.external.ContactInfoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HelpViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var contactInfoRepository: ContactInfoRepository

    private lateinit var viewModel: HelpViewModel

    private lateinit var contactInfo: ContactInfo

    @Before
    fun setUp() {
        contactInfo = makeContactInfo()

        doReturn(flow { emit(contactInfo) }).`when`(contactInfoRepository)
            .getContactInfo(1L)
        viewModel = HelpViewModel(contactInfoRepository)
    }

    @Test
    fun instagramInfo_shouldReturnContactInfoInstagramInFlow() = runTest {
        viewModel.instagramInfo.test {
            val currentInstagramInfo = awaitItem()

            Assert.assertEquals(contactInfo.contactInstagram, currentInstagramInfo)
            cancel()
        }
    }

    @Test
    fun whatsAppInfo_shouldReturnContactInfoWhatsAppInFlow() = runTest {
        viewModel.whatsAppInfo.test {
            val currentWhatsAppInfo = awaitItem()

            Assert.assertEquals(contactInfo.contactWhatsApp, currentWhatsAppInfo)
            cancel()
        }
    }
}