# GifSnap
Gif Snap is a giphy explorer application

Summary: 
I developed an Android application that displays a collection of GIFs by using Paging, Flow, Retrofit, Room, Kotlin Coroutines and Dependency Injection with Dagger Hilt.

Technologies Used:
Paging 3 Library, Flow, Retrofit, Room, Dagger Hilt, Resource Class, Kotlin Coroutines, MVVM Architecture, Jetpack Compose

Key Features:
~ Data Fetching: The app fetches GIFs from the Giphy API using Retrofit. Data is then stored locally in a Room database for offline access.

~ Paging & Flow: The use of Flow ensures that data is fetched asynchronously, avoiding blocking the main thread.

~ Room Database: A Room database is used for storing GIF data locally.

~ Background Gradient in Detail Screen: The Detail Screen of the app features a dynamic background gradient that is based on the dominant color extracted from the GIF. The gradient transitions between a default color and the dominant color. 

~ Shimmer Effect: To improve the user experience while loading GIF data, the app includes a Shimmer Effect. An extension function is added to the Modifier to easily apply the shimmer effect to any item while it is being loaded. This provides a visual loading indicator.

~ Optimized Image Loading: The SubcomposeAsyncImage composable is used in combination with rememberAsyncImagePainter to load images asynchronously while managing different states like loading, error, and success.

~ Custom Image Loader: A custom ImageLoader is created with a limited parallelism dispatcher to manage image loading. This ensures the app does not overload the system when fetching multiple images simultaneously. The image loader also includes specialized decoders for different versions of Android (using ImageDecoderDecoder.Factory() for API 28+ and GifDecoder.Factory() for lower versions) to ensure optimal decoding based on the device's API level.

~ Data Management: The app uses a Resource class to manage the flow of data, providing handling of success, error, and loading states.

~ Kotlin Coroutines: Coroutines are used to offload network calls and database queries from the main thread, used for fetching data from the Giphy API or Room database.
