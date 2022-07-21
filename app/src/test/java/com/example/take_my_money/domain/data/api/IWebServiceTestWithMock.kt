package com.example.take_my_money.domain.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.take_my_money.data.api.IWebService
import com.example.take_my_money.presentation.utils.Constants
import javax.net.ssl.HttpsURLConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class IWebServiceTestWithMock {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private val scope = TestScope()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun `read sample success json file mock`() {
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `read sample failed json file mock`() {
        val reader = MockResponseFileReader("failed_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch details and check response Code 200 returned`() = runBlocking {
        // Given
        val response = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        // When
        val actualResponse = IWebService.getBaseUrl().getAllCoins(Constants.API_KEY1)
        // Then
        assertEquals(
            response.status.contains("200"),
            actualResponse.toString().contains("200")
        )
    }

    @Test
    fun `fetch details for failed response 400 returned`() = runBlocking {
        // Given
        val response = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader("failed_response.json").content)
        mockWebServer.enqueue(response)

        // When
        val actualResponse = IWebService.getBaseUrl().getAllCoins(Constants.API_KEY1)

        // Then
        assertEquals(
            response.status.contains("400"),
            actualResponse.toString().contains("400")
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        val mockWebServer = MockWebServer()
        mockWebServer.shutdown()
    }
}
