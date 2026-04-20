# IPGeoLocation

IPGeoLocation is a modern Android application built with Jetpack Compose that allows users to fetch and store geographical information about any IP address or domain name. It utilizes the [ip-api.com](http://ip-api.com/) service to retrieve detailed location data, including country, region, city, ISP, and more.

## Features

- **IP & Domain Search**: Search for geographical details using either an IP address or a domain name.
- **Smart Offline Caching**: Results are stored locally in a Room database. The app prioritizes cached data and refreshes it if it's older than 5 minutes.
- **Modern UI**: Built entirely with Jetpack Compose for a reactive and fluid user experience.
- **Input Validation**: Includes validation to ensure only valid IP formats or domain names are searched.
- **Dark Mode Support**: Fully compatible with Material 3 dynamic color and themes.

## Tech Stack

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For building the declarative UI.
- **Retrofit & OkHttp**: For networking and API communication.
- **Room Database**: For local data persistence and caching.
- **Hilt**: For Dependency Injection.
- **Kotlin Coroutines & Flow**: For asynchronous programming and reactive state management.
- **ViewModel**: To handle UI logic and state.
- **KSP (Kotlin Symbol Processing)**: For efficient code generation (used by Room).

## Architecture

The project follows a **Clean Architecture** approach combined with the **MVVM** pattern:

- **UI Layer**: Jetpack Compose screens and Hilt-powered ViewModels.
- **Domain Layer**: Pure Kotlin data models representing the business logic.
- **Data Layer**: 
    - **Local**: Room database for offline persistence.
    - **Remote**: Retrofit for API interactions, abstracted behind a RestClient.
    - **Repository**: Implements a "Single Source of Truth" strategy, coordinating between local and remote data sources.
    - **Mappers**: Dedicated mappers to transform data between Remote Response, Database Entity, and Domain Model layers.

## Getting Started

### Prerequisites

- Android Studio Koala or newer.
- JDK 17 or higher.
- An Android device or emulator running API 24+.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/IPGeoLocation.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on your device or emulator.

## API Reference

This app uses the Free API from [ip-api.com](http://ip-api.com/docs/api:json).
- Base URL: `http://ip-api.com/json/`
- *Note: The free tier of this API does not support HTTPS.*

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
