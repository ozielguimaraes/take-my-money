package com.example.take_my_money.domain

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class RuleTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThread = newSingleThreadContext("UI thread")

    fun initBefore() {
        /*val testScope = TestScope()*/
        /*return Dispatchers.setMain(StandardTestDispatcher(testScope.testScheduler))*/
        Dispatchers.setMain(mainThread)
    }

    fun initTearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThread.close()
    }
}
