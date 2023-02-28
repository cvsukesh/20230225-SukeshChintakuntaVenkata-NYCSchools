package com.nycschools.data.network

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.nycschools.R
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataSource(@ApplicationContext open var context: Context) {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): NYCResponse<T> {
        try {
            if (!isInternetAvailable(context)) {
                return NYCResponse.error(NetworkErrorException(context.resources.getString(R.string.network_error_message)))
            }
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return NYCResponse.success(body)
            }
            return NYCResponse.error(HttpException(response))
        } catch (e: Exception) {
            return NYCResponse.error(e)
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }
}