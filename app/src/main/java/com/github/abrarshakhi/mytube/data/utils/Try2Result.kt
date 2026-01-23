package com.github.abrarshakhi.mytube.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T> networkCall(apiCall: suspend () -> T): Result<T> {
    return try {
        val res = apiCall()
        Result.success(res)
    } catch (_: SocketTimeoutException) {
        Result.failure(Exception("Connection timed out. Please try again."))
    } catch (_: UnknownHostException) {
        Result.failure(Exception("No internet connection"))
    } catch (_: IOException) {
        Result.failure(Exception("Network error occurred"))
    } catch (e: HttpException) {
        Result.failure(Exception("Server error: ${e.code()}"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}