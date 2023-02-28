package com.nycschools.data.network

data class NYCResponse<out T>(val status: Status, val data: T?, val throwable: Throwable?) {
    companion object {
        fun <T> success(data: T): NYCResponse<T> {
            return NYCResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): NYCResponse<T> {
            return NYCResponse(Status.ERROR, null, throwable)
        }
    }
}
