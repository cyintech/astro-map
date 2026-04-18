# 🌌 AstroMap - ISS Tracker & Space Explorer

AstroMap is a modern Android application that allows users to track the **International Space Station (ISS)** in real-time, see who's currently on board, and visualize the ISS location relative to their own position using Google Maps.

## ✨ Features

- **🛰️ Real-time ISS Tracking**: Get the current latitude and longitude of the International Space Station.
- **👨‍🚀 Astronaut Info**: See a list of astronauts currently residing on the ISS.
- **🗺️ Interactive Maps**: Visualize the ISS position on a Google Map with custom markers.
- **📍 Proximity Calculation**: Automatically calculates the distance between your current location and the ISS.
- **📜 History/Recent Locations**: Persistently saves and displays recent location checks using a local database.
- **🔐 Permission Handling**: Seamlessly handles location permissions using Modern Android best practices.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern declarative UI toolkit.
- **DI**: [Hilt](https://dagger.dev/hilt/) - Dependency injection for Android.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) - Type-safe HTTP client for Android.
- **Database**: [Room](https://developer.android.com/training/data-storage/room) - SQLite abstraction layer for local data persistence.
- **Maps**: [Google Maps Compose](https://github.com/googlemaps/android-maps-compose) - Jetpack Compose components for Google Maps.
- **Location**: [Google Play Services Location](https://developers.google.com/android/guides/setup) - For retrieving high-accuracy user location.
- **Async**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html) - Reactive stream processing and asynchronous tasks.
- **Architecture**: Clean Architecture with MVVM (Model-View-ViewModel).

## 🏗 Architecture

AstroMap follows **Clean Architecture** principles to ensure the codebase is maintainable, testable, and scalable:

- **Domain Layer**: Contains Business Logic, Use Cases, and Repository Interfaces.
- **Data Layer**: Implements Repository Interfaces, handles Network (Retrofit) and Local Storage (Room).
- **UI Layer**: Composable screens and ViewModels that manage UI state using `StateFlow`.

## 🚀 Getting Started

### Prerequisites

- Android Studio Iguana or newer.
- A Google Maps API Key (Place it in `AndroidManifest.xml`).

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/AstroMap.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Add your Google Maps API Key in `app/src/main/AndroidManifest.xml`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_API_KEY_HERE" />
   ```
5. Build and run the app on an emulator or physical device.

## 📸 Screenshots

*(Add your screenshots here to make it even more impressive!)*

---
Developed with ❤️ by [Cyril Khokhar]
