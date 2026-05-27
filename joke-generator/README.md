# Random Joke Generator

A fun Android application that fetches random jokes from an external API (JokeAPI) and displays them with various filtering options.

## Features

😂 **Random Jokes** - Fetch jokes from JokeAPI  
📁 **Multiple Categories** - Select from various joke categories  
🔒 **Safe Mode** - Filter out explicit content  
🎨 **Material Design** - Modern and intuitive UI  
📤 **Share Jokes** - Share your favorite jokes with friends  
⚡ **Real-time Loading** - Smooth loading experience with progress indicators  
🌐 **API Integration** - Uses Retrofit for clean API communication  

## Supported Categories

- General
- Programming
- Knock-knock
- Miscellaneous
- Dark
- Pun
- Spooky
- Christmas

## Architecture

Built with **MVVM** and modern Android best practices:

```
┌─────────────────────────────────────┐
│         UI Layer (Activity)         │
│        (MainActivity.kt)            │
└─────────────────────────────────────┘
                  │
┌─────────────────────────────────────┐
│      ViewModel Layer                │
│    (JokeViewModel.kt)               │
│  - Manages UI state                 │
│  - Handles user interactions        │
└─────────────────────────────────────┘
                  │
┌─────────────────────────────────────┐
│    Repository Layer                 │
│  (JokeRepository.kt)                │
│  - Data abstraction                 │
│  - Error handling                   │
└─────────────────────────────────────┘
                  │
┌─────────────────────────────────────┐
│      API Layer (Retrofit)           │
│   (JokeApiService.kt)               │
│   (RetrofitClient.kt)               │
│  - JokeAPI v2 integration           │
│  - Network configuration            │
└─────────────────────────────────────┘
```

## Project Structure

```
joke-generator/
├── src/main/
│   ├── java/com/example/jokegenerator/
│   │   ├── data/
│   │   │   ├── api/
│   │   │   │   ├── JokeApiService.kt       # Retrofit API interface
│   │   │   │   └── RetrofitClient.kt       # Retrofit setup
│   │   │   ├── model/
│   │   │   │   └── Joke.kt                 # Data models
│   │   │   └── repository/
│   │   │       └── JokeRepository.kt        # Data abstraction
│   │   └── ui/
│   │       ├── activity/
│   │       │   └── MainActivity.kt          # Main screen
│   │       └── viewmodel/
│   │           └── JokeViewModel.kt         # Business logic
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml           # Main layout
│   │   └── drawable/
│   │       └── spinner_background.xml      # Styling
│   └── AndroidManifest.xml
├── build.gradle.kts                        # Dependencies
└── README.md
```

## Technologies Used

- **Language:** Kotlin
- **Networking:** Retrofit 2 + OkHttp 3
- **Architecture:** MVVM
- **Concurrency:** Coroutines
- **JSON Parsing:** Gson
- **UI Framework:** AndroidX, Material Design 3
- **API:** [JokeAPI v2](https://jokeapi.dev/)

## Dependencies

```gradle
// Retrofit & OkHttp for API calls
implementation("com.squareup.retrofit2:retrofit:2.10.0")
implementation("com.squareup.retrofit2:converter-gson:2.10.0")
implementation("com.squareup.okhttp3:okhttp:4.11.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Gson for JSON parsing
implementation("com.google.code.gson:gson:2.10.1")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

// Jetpack
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Material Design
implementation("com.google.android.material:material:1.11.0")
```

## API Endpoints

The app uses [JokeAPI v2](https://jokeapi.dev/):

**Base URL:** `https://v2.jokeapi.dev/`

### Endpoints

1. **Get Random Joke**
   ```
   GET /jokes/random
   ```
   Fetches a random joke

2. **Get Joke by Category**
   ```
   GET /jokes/random?category={category}
   ```
   Fetches a joke from a specific category

3. **Get Available Categories**
   ```
   GET /info
   ```
   Returns list of available categories

### Query Parameters

- `format`: Response format (default: `json`)
- `safe-mode`: Enable safe mode (default: `false`)
- `category`: Joke category
- `type`: Joke type (`single` or `twopart`)

## How to Use

1. **Launch the app** - Tap the app icon to open
2. **Select Category** - Choose a joke category from the spinner (or "Any" for random)
3. **Enable Safe Mode** - Toggle safe mode if you want family-friendly content
4. **Get Joke** - Tap the "Get Joke" button to fetch a joke
5. **Share** - Tap "Share" to share the joke with friends

## Network Configuration

- **Timeout:** 30 seconds
- **Logging:** Full request/response logging in debug builds
- **Error Handling:** Graceful error messages for network failures
- **Retry Logic:** Automatic retry for failed requests (Retrofit)

## Joke Response Format

```json
{
  "error": false,
  "category": "General",
  "type": "single",
  "joke": "Why don't scientists trust atoms? Because they make up everything!",
  "setup": null,
  "delivery": null,
  "flags": {
    "nsfw": false,
    "religious": false,
    "political": false,
    "racist": false,
    "sexist": false,
    "explicit": false
  },
  "id": 1,
  "safe": true,
  "lang": "en"
}
```

## State Management

The app uses a sealed class to manage UI states:

```kotlin
seal class JokeState {
    object Idle : JokeState()                    // Initial state
    object Loading : JokeState()                 // Fetching joke
    data class Success(val joke: Joke) : JokeState()  // Joke loaded
    data class Error(val message: String) : JokeState() // Error occurred
}
```

## Error Handling

- Network errors are caught and displayed to the user
- Invalid API responses are handled gracefully
- Timeout errors show appropriate messages
- User-friendly error notifications via Toast

## Future Enhancements

- 📚 Search jokes by keyword
- ❤️ Favorite jokes with local storage
- 📊 Joke history tracking
- 🌙 Dark mode support
- 🗣️ Text-to-speech for jokes
- 🎨 Theme customization
- 🔔 Joke of the day notification
- 💾 Offline joke cache

## Testing

The app includes:
- Unit tests for ViewModel logic
- Integration tests for API calls
- UI tests for user interactions

## API Attribution

Jokes are powered by [JokeAPI v2](https://jokeapi.dev/) - A free, open-source API for jokes.

## License

MIT License - Feel free to use this project as a learning resource or template.

## Troubleshooting

**Issue:** No jokes loading
- **Solution:** Check internet connection and ensure JokeAPI is accessible

**Issue:** Safe mode not working
- **Solution:** Toggle safe mode after app initialization

**Issue:** Spinner showing empty
- **Solution:** Wait for categories to load, refresh the app

---

Happy laughing! 😂
