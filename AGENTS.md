# AGENTS.md - Spacecraft Android Development Guide

## Overview
This is an Android application project using Kotlin, Gradle, and Jetpack Compose. The project follows a multi-module architecture with bundles and exports.

## Build Commands

### Gradle Wrapper
```bash
./gradlew              # Build debug APK
./gradlew assembleDebug    # Build debug APK
./gradlew assembleRelease  # Build release APK
```

### Lint
```bash
./gradlew lint                     # Run lint on all modules
./gradlew lintRelease              # Run lint on release build
./gradlew lintDebug                # Run lint on debug build
./gradlew :module-name:lint        # Lint specific module
```

### Testing
```bash
# Run all unit tests
./gradlew test

# Run unit tests for specific module
./gradlew :modules:feeds-bundle:test

# Run a single test class
./gradlew test --tests "com.electrolytej.feeds.ExampleUnitTest"

# Run a specific test method
./gradlew test --tests "com.electrolytej.feeds.ExampleUnitTest.testMethodName"

# Run debug tests
./gradlew testDebugUnitTest

# Run instrumented tests (requires device/emulator)
./gradlew connectedDebugAndroidTest

# Run tests with coverage (if configured)
./gradlew testDebugUnitTestWithCoverage
```

### Module-specific Builds
```bash
# Build specific bundle module
./gradlew :modules:feeds-bundle:assembleDebug

# Build main app
./gradlew :app:assembleDebug
```

### Other Commands
```bash
./gradlew clean                     # Clean build artifacts
./gradlew dependencies              # Show dependencies
./gradlew :module:dependencies      # Module dependencies
./gradlew build                     # Full build with checks
```

## Code Style Guidelines

### Kotlin Style
- Follow [JetBrains Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- Use `kotlin.code.style=official` (configured in gradle.properties)
- Enable incremental and parallel compilation

### Naming Conventions
- **Classes/Objects**: PascalCase (e.g., `Shoe3DPreview`, `DetailActivity`)
- **Functions**: camelCase (e.g., `loadDataWithBaseURL`, `setOnClickListener`)
- **Properties/Variables**: camelCase (e.g., `modelUrl`, `isLoaded`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `EXTRA_MODEL_URL`)
- **Packages**: lowercase (e.g., `com.electrolytej.feeds.widget`)
- **Files**: PascalCase for classes, snake_case for resource files

### Imports
- Use explicit imports (no wildcard imports except for java.lang.*)
- Group imports in this order: Android, Kotlin, Third-party, Project
- Sort alphabetically within groups
- Example:
```kotlin
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.tencent.smtt.sdk.WebView
```

### Formatting
- Use 4 spaces for indentation (not tabs)
- Max line length: 120 characters (soft limit: 200)
- Use semicolons (Kotlin allows omitting, but be consistent with project)
- Open brace on same line for classes/functions
- One blank line between top-level declarations

### Compose Guidelines
- Use `@Composable` annotation for composable functions
- Name composable functions with PascalCase
- Pass `modifier` as first optional parameter after required params
- Use `Modifier.fillMaxSize()` for full-screen components
- Keep composable functions small and focused

### Error Handling
- Use Kotlin null safety (`?`, `?:`, `?.let`)
- Avoid `!!` operator unless absolutely certain
- Use `runCatching` or try-catch for recoverable errors
- Log errors appropriately (android.util.Log)

### Type System
- Use Kotlin's type inference where clear
- Explicit types for public APIs and function parameters
- Use `val` by default, `var` only when mutation is needed
- Leverage sealed classes for exhaustive when expressions

### Android-specific
- Follow Android naming conventions for resources
- Use ViewBinding or Compose instead of findViewById
- Configure WebView with proper settings for JavaScript
- Request INTERNET permission in manifest for WebView features

### Architecture
- Follow clean architecture principles
- Use MVVM pattern for UI layers
- Separate concerns: UI (Compose/Views) -> ViewModel -> Repository -> DataSource
- Use dependency injection (Hilt is configured in this project)

### Gradle
- Use version catalog (libs.versions.toml) for dependencies
- Avoid hardcoding versions in build.gradle files
- Use `api` for dependencies that should be exposed to consumers
- Use `implementation` for internal dependencies

### Common Issues
- **WebView loadDataWithBaseURL**: Make sure to use X5 WebView (com.tencent.smtt.sdk.WebView) for this project
- **Compose dependencies**: Use platform(libs.compose.bom) to manage versions
- **Kotlin/Compose plugin**: Ensure kotlin.android plugin is applied before compose

### Testing
- Write unit tests for ViewModels and business logic
- Use descriptive test names: `testMethodName_Scenario_ExpectedResult`
- Follow AAA pattern: Arrange, Act, Assert
- Mock dependencies appropriately
