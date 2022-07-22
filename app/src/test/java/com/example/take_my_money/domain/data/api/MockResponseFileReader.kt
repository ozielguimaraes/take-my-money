package com.example.take_my_money.domain.data.api

import java.io.InputStreamReader

class MockResponseFileReader(responseJsonMock: String) {

    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(responseJsonMock))
        content = reader.readText()
        reader.close()
    }
}
