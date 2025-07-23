# Teebay Android Project

This document provides instructions on how to set up and run the Teebay Android application on your
local development machine.

## Prerequisites

Before you begin, ensure you have the following installed:

* **Android Studio:** The official IDE for Android development. You can download it from
  the [Android Developer website](https://developer.android.com/studio).
* **JDK (Java Development Kit):** Android Studio usually comes with its own embedded JDK, but a
  standalone installation of JDK 11 or higher is recommended.
* **Android SDK:** Make sure you have a recent version of the Android SDK installed through the SDK
  Manager in Android Studio.

## Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/saeedus/Teebay.git
   cd Teebay
   ```

## How to Run the Application

There are two primary ways to run the application: using Android Studio or from the command line.

### Method 1: Android Studio (Recommended)

1. **Open the project:**
    * Launch Android Studio.
    * Select **File > Open** (or **Open an Existing Project** from the welcome screen).
    * Navigate to the cloned `Teebay` directory and select it.

2. **Sync Project with Gradle Files:**
    * Android Studio will automatically detect the Gradle files (`build.gradle.kts`) and prompt you
      to sync the project. This will download all the necessary dependencies. If it doesn't happen
      automatically, you can trigger it by clicking the "Sync Project with Gradle Files" button in
      the toolbar.

3. **Run the App:**
    * Select a target device from the dropdown menu in the toolbar (e.g., a physical device
      connected via USB or a virtual device from the AVD Manager).
    * Click the **Run 'app'** button (the green play icon) to build and install the application on
      your selected device.

### Method 2: Command Line

You can also build and install the application using the Gradle wrapper (`gradlew`) included in the
project.

1. **Navigate to the project root directory** in your terminal.

2. **(For macOS/Linux) Make the Gradle wrapper executable:**
   ```bash
   chmod +x ./gradlew
   ```

3. **Build and Install the Debug APK:**
   Run the following command to compile the debug version of the app and install it on a connected
   Android device or running emulator.
   ```bash
   ./gradlew installDebug
   ```
   *Note: The project has multiple build flavors: `dev`, `prod`, and `real`. Each flavor might be
   configured with different backend environments (e.g., different base URLs).*

   **Important for Real Devices:**
   When testing on a physical Android device, it is crucial to use the `realDebug` or `realProd`
   build flavors. These flavors are configured to connect to the appropriate backend environment for
   real devices, which may differ from the environment used by `devDebug` (often configured for
   emulators).

   To build and install a specific flavor, use its corresponding task. For example:
    * For the `dev` flavor (typically for emulators): `./gradlew installDevDebug`
    * For the `real` flavor (recommended for physical devices): `./gradlew installRealDebug`
    * For the `prod` flavor: `./gradlew installProdDebug`

4. Once installed, you can find and launch the **Teebay** app from the app drawer on your device.

## Demo Video

A demo video of the Teebay application is available on Google Drive:
[Teebay Demo Video](https://drive.google.com/drive/folders/1-2OwQQu47W_FDDgss0y82RRJSMb0pIow?usp=sharing)
