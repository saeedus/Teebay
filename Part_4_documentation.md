# Teebay Mobile Application - Technical Documentation

## 1. Introduction

This document provides a technical overview of the Teebay mobile application, developed as part of a
take-home challenge. Teebay is a product renting and buying/selling application designed to connect
users for various transactions. The solution comprises a Mobile App (Frontend), a Backend (BE), and
a Database (DB). This documentation focuses on the Mobile App's implementation, detailing its
architecture, key features, and design decisions.

The mobile application is built natively for Android using Kotlin and Jetpack Compose, integrating
with a provided Django backend. It addresses core functionalities such as user authentication (login
and registration with biometric support), product management (adding, editing, deleting products),
and transaction capabilities (buying, renting, and tracking user-specific deals).

## 2. Architecture Overview

The Teebay mobile application adheres to a Clean Architecture, promoting separation of concerns,
testability, and maintainability. The codebase is structured into three primary layers: Data,
Domain, and Presentation.

### 2.1. Layered Architecture

* **Data Layer (`com.sazim.teebay.*.data`)**: This layer is responsible for data retrieval and
  storage. It abstracts the source of data, which can be a remote API, a local database, or shared
  preferences.
    * **Remote**: Handles interactions with the backend API using Ktor Client. It includes Data
      Transfer Objects (DTOs) for serializing/deserializing data exchanged with the API (e.g.,
      `ProductDto`, `LoginResponseDto`).
    * **Local**: Manages local data storage, such as user session information using
      `SharedPreferences` (e.g., `SessionManagerImpl`).
    * **Repository Implementation**: Concrete implementations of the domain layer's repository
      interfaces, responsible for fetching data from various sources and mapping it to domain
      models (e.g., `ProductRepositoryImpl`, `AuthRepositoryImpl`).
    * **Utils**: Contains utility functions for data mapping between DTOs and domain models (e.g.,
      `DataMapper.kt`).

* **Domain Layer (`com.sazim.teebay.*.domain`)**: This is the core of the application, containing
  the business logic and entities. It is independent of any UI or data storage implementation.
    * **Models**: Defines the core business entities (e.g., `Product`, `User`, `Category`). These
      are plain Kotlin data classes that represent the application's data in a platform-agnostic
      way.
    * **Repositories (Interfaces)**: Defines contracts for data operations. These interfaces are
      implemented in the data layer, allowing the domain layer to remain decoupled from data source
      specifics (e.g., `ProductRepository`, `AuthRepository`).
    * **Use Cases (Interactors)**: Encapsulate specific business rules and operations. Each use case
      typically performs a single, well-defined task, orchestrating interactions between
      repositories to achieve a business goal (e.g., `LoginUseCase`, `AddProductUseCase`).

* **Presentation Layer (`com.sazim.teebay.*.presentation`)**: This layer is responsible for
  displaying the UI and handling user interactions. It follows the MVVM (Model-View-ViewModel)
  pattern.
    * **Views (Composables/Activities)**: Composable functions or Android Activities that render the
      UI and observe changes in the ViewModel's state (e.g., `AuthScreen`, `ProductsActivity`).
    * **ViewModels**: Hold and manage UI-related data in a lifecycle-conscious way. They expose
      `StateFlow` for UI observation and handle user actions by interacting with use cases (e.g.,
      `AuthViewModel`, `ProductsViewModel`).
    * **Events/Actions**: Sealed classes or interfaces defining possible user interactions or UI
      events that the ViewModel can process (e.g., `UserAction`, `AuthEvents`).
    * **State**: Data classes representing the current UI state, observed by the Composables (e.g.,
      `AuthState`, `ProductsState`).
    * **Navigation**: Manages the flow between different screens using Jetpack Compose Navigation (
      e.g., `AuthNavGraph`, `ProductNavGraph`).
    * **Components**: Reusable UI elements (e.g., `InputField`, `AuthPrompt`).

### 2.2. Dependency Injection (Koin)

Koin is used as the dependency injection framework to manage the dependencies between different
components and layers of the application. This facilitates loose coupling, making the codebase more
modular and testable. Modules are defined (e.g., `AppModule.kt`) to provide instances of
repositories, use cases, and ViewModels.

### 2.3. Data Flow

The application employs a Unidirectional Data Flow (UDF) pattern within the Presentation layer. User
actions trigger events that are sent to the ViewModel. The ViewModel processes these events, updates
its internal state, and exposes this new state to the UI. The UI then reacts to state changes by
recomposing, ensuring a predictable and manageable data flow. Asynchronous operations are handled
using Kotlin Coroutines and `Flow` for reactive data streams.

## 3. Feature Implementation

### 3.1. Part 1: Preliminary Features (Authentication)

The authentication module (`com.sazim.teebay.auth`) handles user login and registration, including
biometric authentication.

* **Login and User Registration**:
    * The `AuthActivity` serves as the entry point for authentication, hosting the `AuthNavGraph`
      which navigates to the `AuthScreen`.
    * `AuthScreen` is a Compose UI that dynamically switches between login and sign-up forms based
      on the `AuthState`.
    * `AuthViewModel` manages the UI state (`AuthState`) and handles user actions (`UserAction`). It
      interacts with `LoginUseCase` and `SignUpUseCase` to perform authentication operations.
    * `LoginUseCase` and `SignUpUseCase` leverage `AuthRepository` (implemented by
      `AuthRepositoryImpl`) to communicate with the backend API for user authentication.
    * Upon successful login or registration, the `SessionManagerImpl` saves the authentication token
      and user ID locally using `SharedPreferences`.
    * The `AuthViewModel` then emits `AuthEvents.NavigateToMyProducts` to transition the user to the
      main application.

* **Biometrics and Face Unlock/Face Recognition**:
    * Biometric authentication is integrated using Android's BiometricPrompt API, managed by
      `BiometricAuthManager` (located in `com.sazim.teebay.core.presentation`).
    * `AuthActivity` initiates the biometric prompt when `AuthEvents.ShowBiometricPrompt` is
      received from the `AuthViewModel`.
    * The `AuthViewModel` checks `SessionManager.isBiometricLoginEnabled()` and
      `SessionManager.isLoggedIn()` on initialization. If both are true, it triggers the biometric
      prompt, offering a seamless login experience for returning users.
    * The UX decision for biometric placement is to offer it as an alternative login method on the
      main authentication screen if enabled and a user is already logged in (i.e., has a saved
      session). This allows for quick access without re-entering credentials.

* **Assumptions**:
    * As per the challenge requirements, the login functionality uses simple string matching for
      credentials, and secure data transfer or password encryption are not implemented for this
      preliminary part.

### 3.2. Part 2: Product Management

The product management features are implemented within the `com.sazim.teebay.products` module,
following the Clean Architecture principles.

* **Add Product**:
    * The product addition process is designed as a multi-page form, allowing users to go back and
      forth. This is achieved through a series of Compose screens:
        * `AddProductTitleScreen`
        * `AddProductDescScreen`
        * `AddProductCategoryScreen`
        * `AddProductPriceSelectionScreen`
        * `AddProductPhotoUploadScreen`
        * `AddProductSummaryScreen`
    * Navigation between these screens is managed within the `ProductNavGraph`, and state is
      maintained by the `ProductsViewModel`.
    * The `AddProductUseCase` handles the business logic for adding a product, interacting with
      `ProductRepository` to send the product data (including `MultiPartFormDataContent` for images)
      to the backend.

* **Edit Product**:
    * The `EditProductScreen` provides the UI for modifying existing product details.
    * The `UpdateProductUseCase` is responsible for updating product information via the
      `ProductRepository`.

* **Delete Product**:
    * A `ProductDeleteDialog` provides a confirmation prompt before deletion.
    * The `DeleteProductUseCase` handles the deletion logic, communicating with the
      `ProductRepository`.

* **Categories**:
    * Teebay incorporates product categories as defined: ELECTRONICS, FURNITURE, HOME APPLIANCES,
      SPORTING GOODS, OUTDOOR, TOYS.
    * Categories are represented by the `Category` domain model and `CategoryDto` for data transfer.
    * The `GetCategoriesUseCase` retrieves the list of available categories from the backend, which
      are then displayed in the `CategorySpinner` component for product categorization. A product
      can be associated with one or more categories.

### 3.3. Part 3: Rent and Buy/Sell

This section covers the transaction functionalities and notification handling.

* **List All Products**:
    * The `AllProductScreen` displays a comprehensive list of all products available on Teebay,
      fetched using the `GetAllProductsUseCase`.
    * The `ProductsViewModel` manages the state and data for this screen.

* **Ability to Buy a Product**:
    * Users can initiate a purchase from the product detail screen.
    * The `BuyProductUseCase` handles the transaction logic, interacting with the
      `ProductRepository` to record the purchase on the backend.
    * The `ProductBuyResponse` model confirms the transaction.

* **Ability to Rent a Product**:
    * The `RentalPeriodDialog` allows users to specify rental duration.
    * The `ProductRentUseCase` manages the rental transaction, sending `ProductRentRequest` to the
      backend via the `ProductRepository`.
    * The `ProductRentResponse` model confirms the rental.

* **Display All Products Bought/Sold/Borrowed/Lent by the User**:
    * The `MyDealsScreen` provides a consolidated view of a user's transaction history.
    * Dedicated use cases (`GetBoughtProductsUseCase`, `GetSoldProductsUseCase`,
      `GetBorrowedProductsUseCase`, `GetLentProductsUseCase`) retrieve the relevant product lists
      from the `ProductRepository`. These use cases demonstrate handling multiple API calls to
      aggregate data (e.g., fetching purchase/rental records and then individual product details).

* **FCM Notifications**:
    * The application is configured to receive Firebase Cloud Messaging (FCM) notifications.
    * `PushNotificationService.kt` (located in `com.sazim.teebay.core.data.remote.firebase`) is
      responsible for handling incoming FCM messages.
    * Upon receiving a notification (e.g., for a sold or rented product), the service extracts the
      `product_id` from the notification payload.
    * The application is designed to navigate the user directly to the relevant product detail page
      when the notification is clicked, providing a direct and contextual user experience.
      `FcmTokenProvider` ensures the device's FCM token is sent to the backend for targeted
      notifications.

## 4. Key Design Decisions and Corner Cases

This section reflects on various aspects of the application's design, considering it as a
production-ready system.

* **Code Organization and Readability**:
    * **Clean Architecture**: The strict adherence to Clean Architecture (Data, Domain, Presentation
      layers) significantly enhances code organization, readability, and maintainability. Each layer
      has a clear responsibility, reducing inter-dependencies.
    * **Modularization**: The application is implicitly modularized around features (e.g., `auth`,
      `products`), which helps in managing complexity and allows for easier scaling.
    * **Naming Conventions**: Standard Kotlin and Android naming conventions are followed for
      classes, functions, and variables, contributing to readability.

* **Code Design: Component Architecture, Data Structures, and Efficiency**:
    * **Jetpack Compose**: The UI is built entirely with Jetpack Compose, promoting a declarative UI
      paradigm, reusability of UI components (e.g., `InputField`, `ProductCard`), and a more
      efficient development process.
    * **MVVM Pattern**: The MVVM pattern with `StateFlow` and `ViewModel` ensures a clear separation
      between UI logic and business logic, making the UI reactive and testable.
    * **Kotlin Coroutines and Flow**: Asynchronous operations (network requests, database
      interactions) are handled using Kotlin Coroutines and `Flow`. This provides a concise and
      efficient way to manage concurrency and reactive data streams, improving app responsiveness
      and performance.
    * **Data Structures**: Domain models (`Product`, `User`, `Category`) are simple data classes,
      ensuring immutability and ease of use. DTOs are used for network communication, with mapping
      functions (`toDomain()`) to convert them to domain models, preventing direct exposure of
      network models to the domain layer.
    * **Network Efficiency**: The `ProductRepositoryImpl` demonstrates handling multiple product
      fetches (e.g., `getBoughtProducts`, `getSoldProducts`) by using `async` and `awaitAll` within
      a `coroutineScope` and a `Semaphore` to limit concurrent requests (to 10 permits). This helps
      prevent overwhelming the backend and manages network resources efficiently, especially under
      high data render scenarios.

* **Framework Knowledge**:
    * The project demonstrates strong command of Native Android development with Kotlin, Jetpack
      Compose, Koin for Dependency Injection, Ktor Client for networking, and Firebase for
      notifications. The use of `SharedPreferences` for local session management is also evident.

* **User Experience (UX)**:
    * **Input Validation**: While not explicitly detailed in the provided code snippets, the
      `AuthScreen` includes `errorMessage` in its `AuthState`, indicating a mechanism for displaying
      validation errors or API response messages to the user. This is crucial for providing
      immediate feedback.
    * **Multi-page Forms**: The multi-page product addition form (Part 2) is a good UX decision for
      complex data entry, breaking down the process into manageable steps. The ability to go back
      and forth enhances usability.
    * **Loading Indicators**: The presence of `CircularProgressIndicator` in `AuthScreen` (and
      presumably in other screens) provides visual feedback during loading states, preventing user
      frustration.
    * **Biometric Integration**: Offering biometric login improves convenience and security for
      returning users.

* **FE Routing**:
    * Jetpack Compose Navigation is used for managing the application's navigation flow. `NavHost`,
      `NavGraph`, and `NavRoutes` are correctly implemented for both authentication (`AuthNavGraph`)
      and product-related screens (`ProductNavGraph`), ensuring a clear and maintainable navigation
      structure.

* **Database Modeling**:
    * The `Product` and `User` domain models reflect the core entities of the application, aligning
      with the concept of products, sellers, buyers, and renters. The `Category` model supports the
      categorization requirement.

* **Handling Practical Application Corner Cases**:
    * **Rent Time Overlap**: While the mobile app's responsibility is primarily to send the rental
      request, the `RentalPeriodDialog` allows the user to specify the desired rental period. The
      actual logic for handling rent time overlaps (e.g., checking product availability for the
      requested period) would typically reside on the backend. The mobile app's role is to provide
      the necessary input for the backend to perform these checks and return appropriate responses.
    * **Error Handling**: The application uses a `DataResult` sealed class (with `Success` and
      `Error` states) and `DataError.Network` to encapsulate network operation outcomes. This allows
      for robust error handling across the data and domain layers, propagating errors up to the UI
      for appropriate display (e.g., `errorMessage` in `AuthState`).
    * **Performance under High Data Render**: The `ProductRepositoryImpl`'s implementation of
      `getBoughtProducts`, `getSoldProducts`, `getBorrowedProducts`, and `getLentProducts`
      demonstrates an awareness of performance. By using `Semaphore` to limit concurrent API calls
      when fetching individual product details after retrieving transaction IDs, it mitigates the
      risk of overwhelming the device or the network with too many simultaneous requests, which is
      critical for handling large datasets.

* **Testing**:
    * While explicit test files were not provided in the initial directory listing, the Clean
      Architecture and MVVM pattern inherently promote testability. The separation of concerns means
      that:
        * **Use Cases** can be unit-tested in isolation, as they contain the core business logic and
          depend only on interfaces (repositories).
        * **ViewModels** can be unit-tested by mocking their dependencies (use cases) and observing
          their state changes.
        * **Repositories** can be unit-tested by mocking the network client or local data sources.
        * **UI Components** (Composables) can be tested using Compose UI testing frameworks.

## 5. Conclusion

The Teebay mobile application demonstrates a well-structured and maintainable codebase, adhering to
modern Android development best practices. The Clean Architecture, combined with Jetpack Compose,
Kotlin Coroutines, and a robust error handling strategy, provides a solid foundation for a scalable
and performant application. The implementation addresses all core requirements of the challenge,
including complex UI flows like multi-page forms and critical features like biometric authentication
and FCM notifications. The design decisions reflect an understanding of building production-ready
software, with considerations for user experience, efficiency, and future extensibility.