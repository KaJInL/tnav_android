# TNav - Android (Kotlin)

[![Maven Central](https://img.shields.io/maven-central/v/com.kajlee/tnav)](https://central.sonatype.com/artifact/com.kajlee/tnav)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

English | [中文](README.md)

An Android navigation library based on Jetpack Compose Navigation, providing a type-safe, zero-serialization-overhead page navigation solution.

## ✨ Features

- ✅ **Zero Serialization Overhead**: Parameters are stored directly in memory, no JSON serialization/deserialization required
- ✅ **Type Safety**: Uses Kotlin generics to ensure type safety
- ✅ **Concise API**: Pages don't need to pass `NavBackStackEntry` parameters
- ✅ **Support for Any Object**: Can pass any Kotlin object, including sealed classes, Lambdas, etc.
- ✅ **Automatic Memory Management**: Parameters are automatically cleaned up when pages are destroyed, no manual management needed
- ✅ **Rich Animation Effects**: Provides multiple preset animations with support for custom animations
- ✅ **Return Result Support**: Supports returning result data when navigating back

## 📦 Installation

Add the dependency in `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.kajlee:tnav:{version}")
}
```

## 🚀 Quick Start

```kotlin
import com.kajlee.tnav.Destination
import com.kajlee.tnav.Nav
import com.kajlee.tnav.NavigationEffect
import com.kajlee.tnav.composableWithDestination

// 1. Define routes
object GlobalDes {
    object Splash : Destination("Splash")
    object Main : Destination("Main")
    object Login : Destination("Login")
}

// 2. Register routes
fun NavGraphBuilder.registerGlobalRoute() {
    composableWithDestination(GlobalDes.Splash) {
        SplashScreen()
    }
    
    composableWithDestination(GlobalDes.Main) {
        MainScreen()
    }
    
    composableWithDestination(GlobalDes.Login) {
        LoginScreen()
    }
}

// 3. Setup navigation entry
@Composable
fun NavigationScreen() {
    NavigationEffect(startDestination = GlobalDes.Splash.route) {
        registerGlobalRoute()
    }
}

// 4. Navigate to page
Nav.to(GlobalDes.Login)

// 5. Navigate back
Nav.back()
```

## 📖 Usage

### Basic Navigation

#### Simple Navigation

```kotlin
// Navigate to login page
Nav.to(GlobalDes.Login)

// Navigate back
Nav.back()
```

#### Navigation with Parameters

```kotlin
// Define parameter class
data class UserInfo(
    val id: String,
    val name: String,
    val age: Int
)

// Pass parameters when navigating
val userInfo = UserInfo("001", "John", 25)
Nav.to(GlobalDes.UserDetail, params = userInfo)

// Get parameters in target page
@Composable
fun UserDetailScreen() {
    val userInfo = Nav.getParams<UserInfo>()
    
    if (userInfo != null) {
        Text("User: ${userInfo.name}")
    }
}
```

#### Replace Current Page

```kotlin
// Replace current page (remove current page, navigate to new page)
// Suitable for scenarios like navigating to home page after login
Nav.replace(GlobalDes.Main)
```

#### Clear Stack and Navigate

```kotlin
// Clear all pages and navigate to login page
// Suitable for scenarios like logout
Nav.offAllTo(GlobalDes.Login)
```

#### Navigate Back to Specific Page

```kotlin
// Navigate back to specific page (excluding target page)
Nav.back(destination = GlobalDes.Main, inclusive = false)

// Navigate back to specific page (including target page)
Nav.back(destination = GlobalDes.Main, inclusive = true)
```

#### Navigate and Pop Up to Specific Page

```kotlin
// Navigate to detail page and pop up to home page (excluding home page)
Nav.to(
    destination = GlobalDes.Detail,
    popUpToRoute = GlobalDes.Main.route,
    inclusive = false
)

// Navigate to detail page and pop up to home page (including home page)
Nav.to(
    destination = GlobalDes.Detail,
    popUpToRoute = GlobalDes.Main.route,
    inclusive = true
)
```

#### Single Top Mode

```kotlin
// If the page already exists in the stack, reuse it instead of creating a new instance
// Suitable for scenarios to avoid creating duplicate pages
Nav.to(GlobalDes.Main, isSingleTop = true)
```

### Parameter Passing

#### Basic Object Passing

```kotlin
// Pass data class
data class UserInfo(val id: String, val name: String)
Nav.to(GlobalDes.UserDetail, params = UserInfo("001", "John"))

// Receive parameters
@Composable
fun UserDetailScreen() {
    val userInfo = Nav.getParams<UserInfo>()
    // Use userInfo...
}
```

#### Complex Object Passing

```kotlin
// Pass complex data containing List, Map, nested objects
data class ComplexData(
    val items: List<String>,
    val metadata: Map<String, String>,
    val config: Config
)

data class Config(val enabled: Boolean, val timeout: Long)

val complexData = ComplexData(
    items = listOf("item1", "item2"),
    metadata = mapOf("key1" to "value1"),
    config = Config(enabled = true, timeout = 3000)
)

Nav.to(GlobalDes.ComplexDemo, params = complexData)

// Receive complex object
@Composable
fun ComplexDemoScreen() {
    val complexData = Nav.getParams<ComplexData>()
    // Use directly, no serialization needed
    complexData?.items?.forEach { item ->
        Text(item)
    }
}
```

#### Sealed Class Passing

```kotlin
// Define Sealed Class
sealed class PageState {
    data object Loading : PageState()
    data class Success(val message: String) : PageState()
    data class Error(val errorMessage: String) : PageState()
}

// Pass Sealed Class
val state = PageState.Success("Operation successful")
Nav.to(GlobalDes.Result, params = state)

// Receive Sealed Class
@Composable
fun ResultScreen() {
    val state = Nav.getParams<PageState>()
    when (state) {
        is PageState.Loading -> { /* ... */ }
        is PageState.Success -> { /* ... */ }
        is PageState.Error -> { /* ... */ }
        null -> { /* No data received */ }
    }
}
```

### Return Results

```kotlin
// ========== Step 1: Define result data class ==========
data class SelectResult(
    val selectedId: String,
    val selectedName: String
)

// ========== Step 2: Navigate and get result in calling page ==========
@Composable
fun MainScreen() {
    // Navigate to selection page
    Button(onClick = { Nav.to(GlobalDes.SelectionList) }) {
        Text("Go to Selection")
    }
    
    // Get return result from selection page (automatically observes changes)
    val result = Nav.getResultFor<SelectResult>(GlobalDes.SelectionList)
    
    // Display selected result
    result?.let {
        Text("Selected: ${it.selectedName}")
    }
}

// ========== Step 3: Return result in target page ==========
@Composable
fun SelectionListScreen() {
    Card(onClick = {
        // Return with result
        val result = SelectResult("001", "Option One")
        Nav.back(result = result)
    }) {
        Text("Option One")
    }
}
```

### Animation Effects

#### Using Preset Animations

TNav provides 11 preset animation effects, very easy to use:

```kotlin
// Use elastic scale animation
composableWithDestination(
    destination = GlobalDes.Detail,
    transitions = NavTransitions.Elastic
) {
    DetailScreen()
}

// Use fade animation
composableWithDestination(
    destination = GlobalDes.Login,
    transitions = NavTransitions.Fade
) {
    LoginScreen()
}

// Use scale animation
composableWithDestination(
    destination = GlobalDes.Profile,
    transitions = NavTransitions.Scale
) {
    ProfileScreen()
}
```

#### Available Preset Animations

| Animation Name | Effect Description | Use Case |
|---------------|-------------------|----------|
| `NavTransitions.Default` | Horizontal slide (default) | General scenarios |
| `NavTransitions.Fade` | Fade in/out | Lightweight transitions |
| `NavTransitions.Scale` | Scale + fade | Dialogs, detail pages |
| `NavTransitions.SlideVertical` | Vertical slide | Bottom sheets, list expansion |
| `NavTransitions.Elastic` | Elastic scale | Emphasis, important pages |
| `NavTransitions.SlideFade` | Slide + fade | Smooth transitions |
| `NavTransitions.ScaleSlide` | Scale + slide combination | Rich visual effects |
| `NavTransitions.BottomSheet` | Pop from bottom | Bottom sheets, drawers |
| `NavTransitions.RotateScale` | Rotate + scale | Special effect pages |
| `NavTransitions.QuickFade` | Quick fade | Fast switching |
| `NavTransitions.None` | No animation | Performance optimization scenarios |

### Dialog Navigation

```kotlin
// Register dialog route
fun NavGraphBuilder.registerDialogRoute() {
    dialogWithDestination(GlobalDes.ConfirmDialog) {
        ConfirmDialogScreen()
    }
}

// Open dialog
Nav.to(GlobalDes.ConfirmDialog)

// Close dialog
Nav.back()
```

## 🔧 Customization

### Custom Animation

If you need to fully customize animations, you can use the raw API:

```kotlin
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween

composableWithDestination(
    destination = GlobalDes.Detail,
    enterTransition = {
        fadeIn(animationSpec = tween(300))
    },
    exitTransition = {
        fadeOut(animationSpec = tween(300))
    }
) {
    DetailScreen()
}
```

### Create Custom Animation Configuration

You can also create your own animation configuration:

```kotlin
val myCustomTransition = TransitionConfig(
    enter = {
        scaleIn(
            initialScale = 0.9f,
            animationSpec = tween(400, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(400))
    },
    exit = {
        scaleOut(
            targetScale = 0.9f,
            animationSpec = tween(300, easing = FastOutLinearInEasing)
        ) + fadeOut(animationSpec = tween(300))
    },
    popEnter = {
        slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(300)
        )
    },
    popExit = {
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(300)
        )
    }
)

// Use custom animation
composableWithDestination(
    destination = GlobalDes.Detail,
    transitions = myCustomTransition
) {
    DetailScreen()
}
```

### Deep Link Support

```kotlin
composableWithDestination(
    destination = GlobalDes.Detail,
    deepLinks = listOf(
        NavDeepLinkRequest.Builder
            .fromUri("https://example.com/detail".toUri())
            .build()
    )
) {
    DetailScreen()
}
```

## 📚 API Reference

### Nav Object

| Method | Description |
|--------|-------------|
| `Nav.to()` | Navigate to specified page |
| `Nav.back()` | Navigate back |
| `Nav.replace()` | Replace current page |
| `Nav.offAllTo()` | Clear stack and navigate |
| `Nav.getParams<T>()` | Get current page parameters (Composable) |
| `Nav.getResultFor<T>(destination)` | Get return result from specified page (Composable) |
| `Nav.clearCurrentData()` | Clear current page parameters (Composable) |

### NavGraphBuilder Extensions

| Function | Description |
|----------|-------------|
| `composableWithDestination()` | Register page route |
| `dialogWithDestination()` | Register dialog route |

### Complete API Signatures

#### to()

```kotlin
fun to(
    destination: Destination,
    popUpToRoute: String? = null,
    inclusive: Boolean = false,
    isSingleTop: Boolean = false,
    params: Any? = null
)
```

#### back()

```kotlin
fun back(
    destination: Destination? = null,
    inclusive: Boolean = false,
    result: Any? = null
)
```

#### replace()

```kotlin
fun replace(
    destination: Destination,
    isSingleTop: Boolean = false,
    params: Any? = null
)
```

#### offAllTo()

```kotlin
fun offAllTo(
    destination: Destination,
    params: Any? = null
)
```

#### getParams()

```kotlin
@Composable
fun <T> getParams(): T?
```

#### getResultFor()

```kotlin
@Composable
fun <T> getResultFor(destination: Destination): T?
```

#### composableWithDestination()

```kotlin
fun NavGraphBuilder.composableWithDestination(
    destination: Destination,
    transitions: TransitionConfig = NavTransitions.Default,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.() -> Unit
)
```

#### dialogWithDestination()

```kotlin
fun NavGraphBuilder.dialogWithDestination(
    destination: Destination,
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
)
```

## ❓ FAQ

### Q: Why is serialization not needed?

A: Parameters are stored directly in a `ConcurrentHashMap` in memory, associated via UUID as the key. This approach avoids serialization overhead while supporting passing any Kotlin object.

### Q: Will memory leak?

A: No. The system automatically cleans up navigation parameters when pages are destroyed, no manual management needed. When a page is removed from the navigation stack, the corresponding data is automatically deleted from memory.

### Q: Can I pass Lambdas?

A: Yes! Since data is stored in memory, you can pass any object that can be held in memory, including Lambdas, callbacks, etc.

### Q: Does it support deep links?

A: Yes. Just pass the `deepLinks` parameter in `composableWithDestination`.

## 🔗 Related Links

- **Maven Central**: https://central.sonatype.com/artifact/com.kajlee/tnav
- **GitHub**: https://github.com/KaJInL/tnav

## 📄 License

Apache License 2.0

**Important Notice**: When using this library, you **must** indicate in your project that you are using the TNav library and retain the original copyright notice. You can add this notice in the following locations:

- In your project's README.md file
- In your app's "About" page
- In your open source project's LICENSE file

Example:

```
This project uses the TNav navigation library
TNav - https://github.com/KaJInL/tnav
Copyright (c) 2024 Kajin
```

---

```
Copyright 2024 Kajin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

