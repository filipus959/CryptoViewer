
# CryptoViewer

CryptoViewer is an Android application that allows users to view cryptocurrency prices and detailed information. The project is built with **Clean Architecture** and utilizes modern technologies such as **Kotlin**, **Jetpack Compose**, **Retrofit**, **Hilt**, **ViewModel**, **Coroutines**, **Room Database**, **Flow**, and follows the **Single Source of Truth (SSOT)** principle.

## Screenshots

![Screenshot of Home Screen](./CryptoViewer/screens
/Screenshot 2024-09-22 170754.png)
![Screenshot of "Chart Screen]([https://private-user-images.githubusercontent.com/56824843/367674471-347129c4-80df-4394-ae56-eb94f93965bc.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjY0NzE5OTUsIm5iZiI6MTcyNjQ3MTY5NSwicGF0aCI6Ii81NjgyNDg0My8zNjc2NzQ0NzEtMzQ3MTI5YzQtODBkZi00Mzk0LWFlNTYtZWI5NGY5Mzk2NWJjLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA5MTYlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwOTE2VDA3MjgxNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPThhY2E1NGZhMTYyMzVkYjBhYWU0MWI4ZGE0MjljNjlkMzljMDE1ZTQzNjE0MTNiM2Y4ODZmOGVmODgyNjc0ZGQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.G3KxoiMptFohNo3nMGlmdf3SutcWBeoN4Hc0m_ZGQW8](https://github.com/filipus959/CryptoViewer/blob/master/screens/Screenshot%202024-09-22%20170952.png?raw=true))
![Screenshot of Details Screen](https://private-user-images.githubusercontent.com/56824843/367674501-02e99bce-97dd-40d7-b24d-817e5d6788e1.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjY0NzE5OTUsIm5iZiI6MTcyNjQ3MTY5NSwicGF0aCI6Ii81NjgyNDg0My8zNjc2NzQ1MDEtMDJlOTliY2UtOTdkZC00MGQ3LWIyNGQtODE3ZTVkNjc4OGUxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA5MTYlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwOTE2VDA3MjgxNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTgwZjdiYTU5ZThkY2ViMzFjZTZhNWUzMzQxOTdjYWYwODFjNWIyNzc3ODFmMTNjZTRlYjI0ZGI2NWQ2MDFmZTUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.Wx3psHs3NiM1A9kKYaR6hP7D6cfhpb7ILxfqN5bXdIc)

## Features

- View live cryptocurrency prices.
- Access detailed information about each cryptocurrency.
- A clean, modern user interface built with **Jetpack Compose**.
- Efficient data handling using **Retrofit** for API calls.
- Local data storage using **Room Database**.
- **Flow** integration for reactive data streams from the database.
- Adheres to the **Single Source of Truth (SSOT)** principle: all data is fetched from the API, stored in the local database, and displayed from there to ensure data consistency.
- Works offline: The app can function without internet access if data has been previously fetched and stored in the database.
- Dependency management with **Hilt**.
- MVVM architecture with **ViewModel** and **Coroutines** for background tasks.
- Fetches real-time data using the free **CoinPaprika API**.


## Continuous Integration

The project includes Continuous Integration (CI) configured with **GitHub Actions**. Automated tests are run on each commit and pull request to ensure the quality and stability of the application. This setup helps maintain code quality and quickly catch issues during development.

## Technologies Used

- **Kotlin**: Primary language for the Android application.
- **Jetpack Compose**: Modern toolkit for building UI.
- **Clean Architecture**: Separation of concerns, modularized codebase.
- **Retrofit**: For making API requests to fetch cryptocurrency data.
- **Room Database**: Local database for storing and managing cryptocurrency data.
- **Flow**: Kotlin's reactive streams for observing data from the Room Database.
- **Hilt**: Dependency injection for managing the application's dependencies.
- **ViewModel**: For managing UI-related data lifecycle-consciously.
- **Coroutines**: For asynchronous programming and handling API calls efficiently.
- **Single Source of Truth (SSOT)**: All data is stored in a single reliable source (Room Database) and consistently served to the UI from there.
- **CoinPaprika API**: Free API for fetching cryptocurrency prices and information.

## Project Structure

The project follows the **Clean Architecture** pattern, ensuring a clear separation of responsibilities and modularity. The structure includes:

- **Domain Layer**: Contains business logic and use cases.
- **Data Layer**: Responsible for data sources such as APIs (Retrofit) and local databases (Room).
- **Presentation Layer**: Handles UI components with Jetpack Compose and ViewModel.

### Data Flow:

CryptoViewer ensures that all data displayed in the app is retrieved from the local **Room Database** following the **SSOT** principle. Data is fetched from the **CoinPaprika API** and stored locally before being displayed in the UI. This approach guarantees data consistency, improves performance, and enables offline capabilities. The app will continue to function without internet access if there are existing entries in the database.

1. **API Call**: Data is fetched from the **CoinPaprika API** using **Retrofit**.
2. **Database Storage**: The fetched data is stored in the local **Room Database**.
3. **UI Display**: The app retrieves data from the database using **Flow** to display it in the UI.

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

### API Setup

CryptoViewer uses the free **CoinPaprika API** to fetch real-time cryptocurrency data. You do not need an API key for basic usage of this API. The application already has the necessary setup to make requests to the CoinPaprika endpoints.

## Dependency Injection with Hilt

CryptoViewer uses **Hilt** for dependency injection. Hilt simplifies the process of managing dependencies, and it is integrated across the entire project to provide objects like repositories, use cases, and view models.

