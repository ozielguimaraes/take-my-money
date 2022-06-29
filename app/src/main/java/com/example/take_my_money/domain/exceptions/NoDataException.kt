package com.example.take_my_money.domain.exceptions

class NoDataException : Exception() {
    override val message: String
        get() = "You requested specific single item that we don\\'t have at this"
}
