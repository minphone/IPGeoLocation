# IPGeoLocation

IPGeoLocation is a modern Android application built with Jetpack Compose that allows users to fetch and store geographical information about any IP address or domain name. It utilizes the [ip-api.com](http://ip-api.com/) service to retrieve detailed location data, including country, region, city, ISP, and more.

## Features

- **IP & Domain Search**: Search for geographical details using either an IP address or a domain name.
- **Offline Caching**: Results are stored locally in a Room database, allowing for quick retrieval of previously searched addresses.
- **Modern UI**: Built entirely with Jetpack Compose for a reactive and fluid user experience.
- **Input Validation**: Includes validation to ensure only valid IP formats or domain names are searched.
- **Dark Mode Support**: Fully compatible with Material 3 dynamic color and themes.

## Tech Stack

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For building the declarative UI.
- **Retrofit & OkHttp**: For networking and API communication.
- **Room Database**: For local data persistence and caching.
- **Kotlin Coroutines & Flow**: For asynchronous programming and reactive state management.
- **ViewModel**: To handle UI logic and state.
- **KSP (Kotlin Symbol Processing)**: For efficient code generation (used by Room).

## Architecture

The project follows the **MVVM (Model-ViewModel-ViewModel)** architecture pattern and clean architecture principles:

- **UI**: Compose screens and ViewModels.
- **Data**: 
    - **Local**: Room database and DAOs for offline storage.
    - **Remote**: Retrofit service for API calls.
    - **Repository**: Acts as a single source of truth, managing data between local and remote sources.

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
