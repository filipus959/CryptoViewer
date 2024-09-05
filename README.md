
# CryptoViewer

CryptoViewer is an Android application that allows users to view cryptocurrency prices and detailed information. The project is built with **Clean Architecture** and utilizes modern technologies such as **Kotlin**, **Jetpack Compose**, **Retrofit**, **Hilt**, **ViewModel**, **Coroutines**, and **Dependency Injection**.

## Features

- View live cryptocurrency prices.
- Access detailed information about each cryptocurrency.
- A clean, modern user interface built with **Jetpack Compose**.
- Efficient data handling using **Retrofit** for API calls.
- Dependency management with **Hilt**.
- MVVM architecture with **ViewModel** and **Coroutines** for background tasks.

## Technologies Used

- **Kotlin**: Primary language for the Android application.
- **Jetpack Compose**: Modern toolkit for building UI.
- **Clean Architecture**: Separation of concerns, modularized codebase.
- **Retrofit**: For making API requests to fetch cryptocurrency data.
- **Hilt**: Dependency injection for managing the application's dependencies.
- **ViewModel**: For managing UI-related data lifecycle-consciously.
- **Coroutines**: For asynchronous programming and handling API calls efficiently.

## Project Structure

The project follows the **Clean Architecture** pattern, ensuring a clear separation of responsibilities and modularity. The structure includes:

- **Domain Layer**: Contains business logic and use cases.
- **Data Layer**: Responsible for data sources such as APIs (Retrofit) and repositories.
- **Presentation Layer**: Handles UI components with Jetpack Compose and ViewModel.

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later.
- Kotlin 1.5 or later.
- Gradle 7.0 or later.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/filipus30/CryptoViewer.git
   ```
2. Open the project in **Android Studio**.
3. Build the project to install necessary dependencies:
   ```bash
   ./gradlew build
   ```
4. Run the app on an emulator or a physical device.

## Dependency Injection with Hilt

CryptoViewer uses **Hilt** for dependency injection. Hilt simplifies the process of managing dependencies, and it is integrated across the entire project to provide objects like repositories, use cases, and view models.

## License

This project is licensed under the MIT License. See the License file for details.
