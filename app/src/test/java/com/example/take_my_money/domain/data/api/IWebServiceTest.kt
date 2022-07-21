package com.example.take_my_money.domain.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.take_my_money.data.api.IWebService
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class IWebServiceTest {

    @OptIn(DelicateCoroutinesApi::class)
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThread = newSingleThreadContext("Ui thread")

    private val apiKeyTest = "BFF5CD2A-62B3-4975-B4D6-FA9E7171B38F"

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThread)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        /*mainThread.close()*/
    }

    @Test
    fun `when call api the return should be different of null`() = runBlocking {
        Assert.assertNotEquals(
            null,
            IWebService.getBaseUrl().getAllCoins(apiKeyTest)
        ) // Test do retorno da URL, Code, Protocolo, etc.
    }

    @Test
    fun `When call api to return o code of response equals 200`() = runBlocking {
        Assert.assertEquals(
            200,
            IWebService.getBaseUrl().getAllCoins(apiKeyTest).code()
        ) // Test o código de resposta da api
    }

    @Test
    fun `When call api to return o code of response to apikey invalided`() = runBlocking {
        Assert.assertEquals(
            401,
            IWebService.getBaseUrl().getAllCoins("apiKeyTest").code()
        ) // Test o código de resposta para apikey inválida
    }

    @Test
    fun `When call api to return o size of list`() = runBlocking {
        Assert.assertNotEquals(
            0,
            IWebService.getBaseUrl().getAllCoins(apiKeyTest).body()?.size
        ) // Test o tamanho da lista de respostas
    }

    @Test
    fun `When call api to return code of protocol`() = runBlocking {
        Assert.assertEquals(
            "h2",
            IWebService.getBaseUrl().getAllCoins(apiKeyTest).raw().protocol.toString()
        ) // Test o retorno de protocolo da api
    }

    @Test
    fun `When call api to return the time of response`() = runBlocking {
        Assert.assertNotEquals(
            0,
            IWebService.getBaseUrl().getAllCoins(apiKeyTest).raw().receivedResponseAtMillis
        ) // Test o tempo de resposta em milliseconds
    }
}
