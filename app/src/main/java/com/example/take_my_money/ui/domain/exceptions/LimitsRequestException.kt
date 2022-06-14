package com.example.take_my_money.ui.domain.exceptions

class LimitsRequestException : Exception() {
    override val message: String
        get() = "You have exceeded your API key rate limits"
}
