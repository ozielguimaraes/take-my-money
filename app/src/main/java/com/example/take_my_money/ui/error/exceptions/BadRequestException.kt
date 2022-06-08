package com.example.take_my_money.ui.error.exceptions

class BadRequestException : Exception() {
    override val message: String
        get() = "There is something wrong with your request"
}

