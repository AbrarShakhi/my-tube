package com.github.abrarshakhi.mytube.data.utils

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
    } catch (_: Exception) {
        Result.failure(Exception("Unable to find this channel"))
    }
}