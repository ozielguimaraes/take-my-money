package com.example.take_my_money.ui.domain.exceptions

class UnauthorizedException : Exception() {
    override val message: String
        get() = "Your API key is wrong"
}
