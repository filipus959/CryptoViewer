package com.filip.cryptoViewer.data.util

import java.io.IOException

class NetworkBoundResource<ResultType, RequestType>(
    private val fetchFromLocal: suspend () -> ResultType?,
    private val fetchFromRemote: suspend () -> RequestType,
    private val saveRemoteResult: suspend (RequestType) -> Unit,
    private val shouldFetchFromRemote: Boolean = true
) {

    suspend fun fetch(): ResultType {
        return try {
            if (shouldFetchFromRemote) {
                val remoteData = fetchFromRemote()
                saveRemoteResult(remoteData)
            }
            fetchFromLocal() ?: throw RuntimeException("No data available in cache")
        } catch (e: IOException) {
            fetchFromLocal()?.let {
                return it
            } ?: throw RuntimeException("Couldn't reach server and no cached data available", e)
        } catch (e: Exception) {
            throw RuntimeException("An error occurred", e)
        }
    }
}
