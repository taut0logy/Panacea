# Panacea

A health management Android application built with Kotlin and Java on Android SDK 34. Panacea provides users with tools to monitor cardiac health, calculate fitness metrics, access condition-specific health guidance, and connect with medical professionals — all backed by Firebase cloud infrastructure.

---

## Overview

Panacea is a multi-feature mobile health application developed collaboratively as a software engineering course project at Khulna University of Engineering and Technology (KUET). The project was built with a strong emphasis on testability, clean architecture, and structured team collaboration using Git version control. The codebase is written in a mix of Kotlin and Java, targeting Android API 26 through 34.

---

## Features

- **User Authentication**: Firebase Authentication-backed registration and login. Passwords are validated on the client against complexity rules before submission. Sessions persist across app restarts, with automatic routing to the home screen for returning users.
- **User Profiles**: Each user stores a name, email, phone number, date of birth, and gender. Profile pictures can be captured with the device camera or selected from the gallery and are stored in Firebase Storage. Profiles are viewable and editable after registration.
- **Cardiac Health Records**: Users can log heart rate, systolic pressure, and diastolic pressure with an optional comment. Records are stored per user in Firebase Realtime Database and displayed in a scrollable history view. Input validation enforces clinical ranges for all three measurements.
- **BMI and BMR Calculator**: Calculates Body Mass Index and Basal Metabolic Rate from age, weight, and height in feet and inches. The calculator uses the Observer design pattern to decouple the calculation logic from the UI layer. Input validation rejects negative values and invalid inch ranges.
- **Health Tips**: Condition-specific health guidance organized into four categories: Children, Elderly, Hypertension, and Diabetic. Each category is rendered as a separate Fragment loaded dynamically through a bottom navigation bar.
- **Doctor Directory**: A list of doctors fetched from Firebase Realtime Database, showing name, department, and availability. Tapping a doctor opens a detail screen with contact information, years of experience, and consultation schedule.
- **Contact Page**: Displays a phone number, email address, and physical address, each tappable to launch the appropriate system intent (dialer, email client, and maps respectively).

---

## Architecture and Design Patterns

The project applies several design patterns deliberately:

- **Observer Pattern**: `BMIandBMRCalculator` maintains a list of `BMIandBMRObserver` subscribers and notifies them on calculation completion, separating computation from presentation.
- **Singleton Pattern**: `AuthUtility`, `UserUtility`, `RecordManager`, and `DoctorManager` are implemented as thread-safe singletons to provide a single point of access for Firebase operations throughout the app.
- **Utility Classes**: `AuthUtility` and `UserUtility` wrap all Firebase Auth and Firebase Database/Storage calls behind callback interfaces (`OnUserCreatedListener`, `OnUserAuthenticatedListener`, etc.), making these operations mockable in unit tests.

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9, Java 8 |
| Platform | Android SDK 34 (min SDK 26) |
| Authentication | Firebase Authentication |
| Database | Firebase Realtime Database |
| File Storage | Firebase Storage |
| Push Notifications | Firebase Cloud Messaging |
| Networking | Retrofit 2.9, OkHttp 4.12 |
| Image Loading | Glide 4.16 |
| UI Components | Material Design 3, CircleImageView, Lottie |
| Build System | Gradle 8.6 (Kotlin DSL) |
| Unit Testing | JUnit 4, JUnit 5, Mockito 5.12 |
| Instrumented Testing | Espresso 3.5, Mockito Android |
| Test Runner Support | Robolectric 4.7 |

---

## Testing

Testing was a central focus of this project. The test suite covers both unit logic and full UI interaction across multiple activity and fragment screens.

### Unit Tests (`app/src/test/`)

Unit tests run on the JVM without a device and use Mockito to mock Firebase dependencies.

- **`AuthUtilityTest`**: Tests the sign-up, sign-in, sign-out, UID retrieval, and account deletion flows against a mocked `FirebaseAuth` and `FirebaseDatabase`. Verifies both success and failure paths for each operation.
- **`UserUtilityTest`**: Tests user creation, retrieval, update, deletion, profile picture upload, and profile picture download against mocked Firebase Database and Storage references. Uses Robolectric to provide an Android context on the JVM.
- **`GenderTest`**: Tests the `Gender` enum for correct integer mappings, valid conversions from integer, and expected `IllegalArgumentException` on invalid input.
- **`RecordTest`**: Tests the `Record` data class constructors, field assignment, and equality of field values between two instances.

### Instrumented Tests (`app/src/androidTest/`)

Instrumented tests run on a connected device or emulator and use Espresso for UI interaction.

- **`BMIActivityTest`**: Verifies that valid input produces the correct BMI result, and that negative values and out-of-range inch values each produce the appropriate inline error message.
- **`RegisterActivityTest`**: Tests the full registration form validation: valid input passes, invalid email format fails, mismatched passwords fail, weak password format fails, wrong date format fails, and short phone numbers fail. Also tests the mock-injected success and failure paths for the registration call.
- **`LoginActivityTest`**: Tests the login flow with a mock `AuthUtility` injected via reflection, verifying that a successful authentication triggers navigation to `HomeActivity`.
- **`WelcomeActivityTest`**: Verifies that the Login and Register buttons navigate to their respective activities using Espresso Intents.
- **`HomeActivityTest`**: Tests that the Records, Health Tips, and BMI cards on the home screen each launch the correct activity.
- **`HealthTipsActivityTest`**: Tests that each fragment type (Child, Diabetic, Elderly, Hypertension) can be loaded into the fragment container and that the `getFrag()` method returns the expected identifier string. Also tests the back button navigation.
- **`RecordActivityTest`**: Tests that the Show Records button navigates to the `ShowRecords` activity.
- **`ContactActivityTest`**: Tests that the phone number, email, and address text views each fire the correct system intent (ACTION\_DIAL, ACTION\_SENDTO, ACTION\_VIEW) when clicked.

### Test Infrastructure

- **`CustomTestRule`**: A custom `ActivityTestRule` subclass that injects mock `FirebaseAuth` and `FirebaseDatabase` instances into `AuthUtility` via reflection before the activity launches, isolating instrumented tests from live Firebase calls.
- **`ToastMatcher`** : A custom Espresso root matcher for asserting the contents of Toast messages.

---

## Version Control and Collaboration

This project was developed by a team of multiple contributors using Git for version control.

**Branch strategy:** Feature branches were used for each major module (authentication, records, health tips, BMI calculator, doctor listing, testing). Pull requests were reviewed before merging into the main branch.

**Commit conventions:** Commits were scoped to individual features or bug fixes to keep history readable and to simplify blame and bisect operations.

**Parallel development:** The separation of concerns across `AuthUtility`, `UserUtility`, and activity-level controllers allowed team members to work on independent modules simultaneously without creating merge conflicts in shared files. The callback interface pattern on Firebase operations made it straightforward for one team member to implement the UI layer against a stub while another completed the backend integration.

**Testing as a collaboration contract:** Unit tests for `AuthUtility` and `UserUtility` served as an agreed interface specification between contributors. Any change to a public method signature or callback behavior would immediately surface as a test failure in another team member's branch, preventing silent breaking changes.

---

## Project Structure

```
app/src/
  main/java/com/project/panacea/
    AuthUtility.java          # Firebase Auth wrapper (Singleton)
    UserUtility.java          # Firebase Database/Storage wrapper (Singleton)
    RecordManager.java        # Cardiac record persistence (Singleton)
    DoctorManager.java        # Doctor data access (Singleton)
    BMIandBMRCalculator.java  # BMI/BMR logic with Observer pattern
    BMIandBMRObserver.java    # Observer interface
    BMIActivity.java          # BMI/BMR screen
    HomeActivity.kt           # Main dashboard
    LoginActivity.java        # Login screen
    RegisterActivity.java     # Registration screen
    RecordActivity.java       # Add cardiac record
    ShowRecords.java          # View record history
    HealthTipsActivity.java   # Health tips host with fragment navigation
    DoctorListActivity.java   # Doctor directory
    DoctorInfoActivity.java   # Doctor detail screen
    ProfileActivity.java      # User profile view
    EditProfileActivity.java  # Profile edit screen
    ContactActivity.java      # Contact information
    User.java                 # User data model
    Doctor.java               # Doctor data model
    Record.java               # Cardiac record data model
    ConsultationHours.java    # Doctor availability model
    Gender.java               # Gender enum
    Weekday.java              # Weekday enum

  test/java/com/project/panacea/
    AuthUtilityTest.java
    UserUtilityTest.java
    GenderTest.java
    RecordTest.java

  androidTest/java/com/project/panacea/
    BMIActivityTest.java
    RegisterActivityTest.java
    LoginActivityTest.java
    WelcomeActivityTest.java
    HomeActivityTest.kt
    HealthTipsActivityTest.java
    RecordActivityTest.java
    ContactActivityTest.java
    CustomTestRule.java
    ToastMatcher.java
```

---

## Setup and Installation

### Prerequisites

- Android Studio Hedgehog or later
- Android SDK 34
- A Firebase project with Authentication, Realtime Database, and Storage enabled

### Firebase Configuration

1. Create a project at [console.firebase.google.com](https://console.firebase.google.com).
2. Register an Android app with the package name `com.project.panacea`.
3. Download the `google-services.json` file and place it in the `app/` directory.
4. Enable Email/Password sign-in under Authentication.
5. Set up a Realtime Database with the following top-level nodes: `users`, `records`, `doctors`.
6. Enable Firebase Storage for profile picture uploads.

### Running the App

Open the project in Android Studio and run it on a connected device or emulator running Android 8.0 (API 26) or higher.

### Running Tests

To run unit tests:

```bash
./gradlew test
```

To run instrumented tests on a connected device:

```bash
./gradlew connectedAndroidTest
```

---

## Input Validation Rules

| Field | Constraint |
|---|---|
| Email | Must match standard email format |
| Password | Minimum 8 characters, requires uppercase, lowercase, digit, and special character |
| Phone Number | Exactly 11 digits |
| Date of Birth | Format DD/MM/YYYY |
| Heart Rate | 0 to 200 |
| Systolic Pressure | 0 to 240, must exceed diastolic |
| Diastolic Pressure | 0 to 240 |
| BMI Height (inches) | Must be less than 12 |
| BMI inputs | All values must be positive |

---

## Authors

Developed as a course project at Khulna University of Engineering and Technology (KUET) by a collaborative team.
