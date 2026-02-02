package com.example.climasense.core.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class WeatherOneCallResponse(
    @SerialName("alerts")
    val alerts: List<WeatherAlert> = arrayListOf(),
    @SerialName("current")
    val current: WeatherData,
    @SerialName("daily")
    val daily: List<WeatherData>,
    @SerialName("hourly")
    val hourly: List<WeatherData>,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
)


@Serializable
data class WeatherData(
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("dt")
    @Serializable(with = LocalDateTimeSerializer::class)
    val dt: LocalDateTime,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("temp")
    @Serializable(with = TempSerializer::class)
    val temp: Temp? = null,
    @SerialName("uvi")
    val uvi: Double,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("weather")
    val weather: List<Weather>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    @SerialName("wind_speed")
    val windSpeed: Double?
)

@Serializable
data class Weather(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String
)

@Serializable
data class Temp(
    @SerialName("day")
    val day: Double?=null,
    @SerialName("eve")
    val eve:  Double?=null,
    @SerialName("max")
    val max:  Double?=null,
    @SerialName("min")
    val min:  Double?=null,
    @SerialName("morn")
    val morn:  Double?=null,
    @SerialName("night")
    val night:  Double?=null,
)

object TempSerializer :
    JsonTransformingSerializer<Temp>(Temp.serializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement =
        when (element) {
            is JsonPrimitive -> buildJsonObject {
                put("day", element)
            }
            is JsonObject -> element
            else -> error("Unexpected temp format")
        }
}

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UnixTimestampAsLocalDateTime", PrimitiveKind.LONG)

    override fun serialize(encoder:
                           Encoder, value: LocalDateTime) {
        // Convert LocalDateTime to epoch seconds (UTC)
        val epochSeconds = value.toEpochSecond(ZoneOffset.UTC)
        encoder.encodeLong(epochSeconds)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        // Read the Unix timestamp and convert to LocalDateTime in UTC
        val epochSeconds = decoder.decodeLong()
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneOffset.UTC)
    }
}