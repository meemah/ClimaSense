package com.example.climasense.core

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okio.IOException
import java.util.Locale
import javax.inject.Inject

class LocationUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    suspend fun getUserLocation(): Location? = getLastKnownLocation() ?: getCurrentLocation()

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation(): Location? =
        suspendCancellableCoroutine { cont ->
            fusedLocationClient.lastLocation
                .addOnFailureListener { exception ->
                    cont.resume(null, null)
                }.addOnSuccessListener { location ->
                    cont.resume(location, null)
                }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation(): Location? =
        suspendCancellableCoroutine { cont ->
            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()
            fusedLocationClient.getCurrentLocation(request, null)
                .addOnFailureListener { exception ->
                    cont.resume(null, null)
                }.addOnSuccessListener { location ->
                    cont.resume(location, null)
                }

        }

    suspend fun getLocationName(latitude: Double, longitude: Double): String? =
        withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())

                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                addresses?.firstOrNull()?.let { address ->
                    listOfNotNull(address.locality, address.adminArea)
                        .joinToString(", ")
                        .ifEmpty { null }
                }
                ""

            } catch (e: IOException) {
                null
            }
        }

}