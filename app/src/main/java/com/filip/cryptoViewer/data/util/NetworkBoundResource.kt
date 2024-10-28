package com.filip.cryptoViewer.data.util

import java.io.IOException
/**
 * A generic class to handle resource fetching that prioritizes remote data while caching it locally.
 * This class is used to minimize network usage by caching data locally
 * and only fetching from the network when necessary.
 *
 * @param ResultType The type of the data returned by local and remote sources after processing.
 * @param RequestType The type of the data fetched from the remote source before any processing or caching.
 *
 * @property fetchFromLocal A suspending function that retrieves the data from a local source, such as a database.
 * @property fetchFromRemote A suspending function that retrieves the data from a remote source, such as an API.
 * @property saveRemoteResult A suspending function that saves the remote data into the local source.
 */
class NetworkBoundResource<ResultType, RequestType>(
    private val fetchFromLocal: suspend () -> ResultType?,
    private val fetchFromRemote: suspend () -> RequestType,
    private val saveRemoteResult: suspend (RequestType) -> Unit,
) {
    /**
     * Fetches the data, prioritizing remote data if available. This function attempts to retrieve the data
     * from the remote source. If the remote data fetch is successful, the data is saved locally and
     * re-fetched from the local source to ensure consistency.
     *
     * If there is an `IOException` (typically a network error) while fetching remote data,
     * it falls back to retrieving from the local source, if available.
     *
     * @return The fetched data of type `ResultType`.
     * @throws RuntimeException if no data is available in the local cache and the remote data cannot be reached
     *                          or an unexpected error occurs.
     */
    suspend fun fetch(): ResultType {
        return try {
            val remoteData = fetchFromRemote()
            saveRemoteResult(remoteData)
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
