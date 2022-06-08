package com.example.take_my_money.ui.error.exceptions

class UnauthorizedException : Exception() {
    override val message: String
        get() = "Your API key is wrong"
}
