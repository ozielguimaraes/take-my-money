package com.example.take_my_money.ui.domain.exceptions

class ForbiddenException : Exception() {
    override val message: String
        get() = "Your API key does\\'t have enough privileges to access this resource"
}
