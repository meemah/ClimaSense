# ClimaSense 🌦

A weather app for Android built with Jetpack Compose, showing current
conditions, a 7-day and hourly forecast, weather alerts, and air quality.

## Screenshots

<!-- add images later: ![Home](screenshots/home.png) -->

## Tech stack

- **UI:** Jetpack Compose + Material 3, type-safe Navigation
- **Architecture:** MVVM + Repository pattern
- **DI:** Hilt
- **Networking:** Ktor + Sandwich (`ApiResponse`)
- **Persistence:** DataStore (theme preference)
- **API:** OpenWeather One Call 3.0

## Setup

1. Get a free API key at https://openweathermap.org/api
2. In the project root, open (or create) `local.properties` and add: WEATHER_API_KEY=your_key_here
3. Build and run.

> `local.properties` is gitignored — the key is injected at build time via
> `BuildConfig.WEATHER_API_KEY`.