# Architecture
Clean Architecture and Dependency Rule
Although the project is not separated into distinct modules, I ensured that it respects the Clean Architecture dependency rule:

Domain Layer: The domain layer is at the core of the architecture. It defines high-level use cases and business logic. Importantly, it depends on nothing outside itself, adhering to the Clean Architecture principle of isolation. 
This separation ensures that the core business logic remains agnostic of the data source and presentation layer.

Data Layer: The data layer is divided into local and remote data sources. While it interacts with the domain layer, it does not depend on the presentation layer. This separation ensures that the data layer can be easily replaced or extended without affecting the overall architecture.

Presentation Layer: This layer interacts primarily with the domain layer. 
It uses ViewModels to encapsulate business logic and provide data for the UI. 
By adhering to the Clean Architecture principles, the presentation layer remains independent of specific data sources, facilitating future modifications and extensions.


## Good practices applied
1. Always using constructor dependency injection so the classes are easy to test.
2. Depending on abstractions rather than implementations.
3. Using mappers for each layer to isolate changes when some data structure changes
4. Defining contracts using generics so in the future similar patterns can be followed for different features. DRY
5. Use of Either class to express success | fail operations in a succinct way.
6. Using sealed classes to express state in a succinct way.
7. Making the UI code dumb, there is no complex logic inside the composable functions, just showing UI state
8. Using a single activity app, and using the navigation component to navigate between screens

## Testing
1. Unit tests for the domain layer
2. Unit tests for the presentation layer



## Technologies
- Compose : For building android UI
- Jetpack Compose Navigation : For navigating between screens
- MVVM : For coordinating presentation layer logic.
- Kotlin Flow : For reactive programming.
- Coroutines: For managing asynchronous operations efficiently.
- Retrofit: For making network requests to fetch user data.
- Dagger-Hilt: For Dependency Injection, promoting modularity and maintainability.
- Mockk : For test doubles.
- AssertJ : For fluent assertions.

## Improvements
These are some improvements that can be done to the project with more time:
1. Add tests for the data layer
2. Add UI tests
3. Add a persistent offline storage to avoid fetching data from the network every time the app is launched
4. Add a loading state to the UI
5. Add a retry button to the UI in case the network request fails
6. Add a cache layer to avoid fetching data from the network every time the app is launched
7. Add a pull to refresh mechanism to the UI
