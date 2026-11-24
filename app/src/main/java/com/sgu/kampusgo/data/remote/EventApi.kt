package com.sgu.kampusgo.data.remote

import android.util.Log
import com.sgu.kampusgo.Event
import java.io.IOException
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.sgu.kampusgo.data.EventsUiState
data class EventResponse(
    val events: List<Event>,
)
interface EventApi {
    @GET("events.json")
    suspend fun events(): Response<EventResponse>
}

object EventApiClient {
    // The board gives the full file address. BASE_URL is that address through the last slash.
    private const val BASE_URL =
        "https://raw.githubusercontent.com/rangganehemiasgu/kampusgo/main/"
    private val api: EventApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApi::class.java)
    }

    suspend fun loadEvents(): EventsUiState {
        return try {
            val response = api.events()
            Log.d("KampusGo", "HTTP ${response.code()}")
            if (response.isSuccessful) {
                EventsUiState.Success(response.body()?.events ?: emptyList())
            } else {
                EventsUiState.Error("Could not load events (HTTP ${response.code()}).")
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Log.d("KampusGo", "No HTTP response: ${e.message}")
            EventsUiState.Error("No network. Check the connection and retry.")
        } catch (e: Exception) {
            Log.d("KampusGo", "Could not read events: ${e.message}")
            EventsUiState.Error("The event list could not be read.")
        }
    }
}

