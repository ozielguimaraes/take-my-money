package com.example.take_my_money.ui.domain.exceptions

class UseInternetException : Exception() {
    override val message: String
        get() = "Check your use of internet, please. \n "
}
