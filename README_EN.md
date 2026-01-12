# TNav - Android (Kotlin)

[![Maven Central](https://img.shields.io/maven-central/v/com.kajlee/tnav)](https://central.sonatype.com/artifact/com.kajlee/tnav)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

English | [中文](README.md)

An Android navigation library based on Jetpack Compose Navigation, providing a type-safe, zero-serialization-overhead page navigation solution.

## ✨ Features

- ✅ **Zero Serialization Overhead**: Parameters are stored directly in memory, no JSON serialization/deserialization required
- ✅ **Type Safety**: Uses Kotlin generics to ensure type safety
- ✅ **Concise API**: Pages don't need to pass `NavBackStackEntry` parameters
- ✅ **Chain API**: Supports functional operators like `map`, `filter`, `unwrapOr`
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

#### Chain API (Recommended)

TNav provides a chain API for parameter handling with `map`, `filter`, `unwrapOr` and other operators.

**Important**: The chain API returns `MutableState<T>`, supporting Compose's `by` delegation syntax and automatically triggers recomposition.

```kotlin
@Composable
fun UserDetailScreen() {
    // ===== Choosing between val and var =====
    // val: read-only access, use when you don't need to modify the parameter
    // var: mutable access, use when you need to modify the parameter in the page (Recommended!)
    
    // 1️⃣ Using val (read-only) - for display only, no modification needed
    // unwrapOr() returns MutableState<UserInfo>, value obtained via by delegation
    val userInfo by Nav.param<UserInfo>().unwrapOr(defaultUser)
    Text("Username: ${userInfo.name}")  // ✅ Read-only access
    // userInfo = newUser  // ❌ Compile error: val cannot be reassigned
    
    // 2️⃣ Using var (mutable) - use when page needs to modify parameter ⭐️Recommended
    // Most real-world scenarios require parameter modification (form editing, state updates, etc.)
    var mutableUser by Nav.param<UserInfo>().unwrapOr(defaultUser)
    
    // ✅ Can reassign, triggers Compose recomposition, UI updates automatically
    Button(onClick = { mutableUser = newUser }) {
        Text("Update User Info")
    }
    
    // ✅ Can modify properties (using data class copy)
    Button(onClick = { 
        mutableUser = mutableUser.copy(name = "New Name") 
    }) {
        Text("Change Name")
    }
    
    // 3️⃣ Real-world use cases for var
    // Use case 1: Form editing
    var formData by Nav.param<FormData>().unwrapOr(FormData())
    TextField(
        value = formData.email,
        onValueChange = { formData = formData.copy(email = it) }
    )
    
    // Use case 2: Counter/State management
    var count by Nav.param<Int>().unwrapOr(0)
    Button(onClick = { count++ }) { Text("Clicks: $count") }
    
    // Use case 3: Toggle state
    var isEnabled by Nav.param<Boolean>().unwrapOr(false)
    Switch(checked = isEnabled, onCheckedChange = { isEnabled = it })
    
    // 4️⃣ Using map for transformation (usually with val, as transformed values are typically not modified directly)
    val userName by Nav.param<UserInfo>().map { it.name }.unwrapOr("Unknown")
    
    // 5️⃣ Using filter
    val adultUser by Nav.param<UserInfo>()
        .filter { it.age >= 18 }
        .unwrapOr(defaultUser)
    
    // 6️⃣ Chain multiple operations
    val vipUserName by Nav.param<UserInfo>()
        .filter { it.age >= 18 }
        .filter { it.isVip }
        .map { it.name }
        .unwrapOr("Not VIP")
        
    // 7️⃣ Using orNull for nullable value (var also works)
    var nullableUser by Nav.param<UserInfo>().orNull()
    
    // 8️⃣ Using unwrapOrElse for lazy default value
    val user by Nav.param<UserInfo>().unwrapOrElse { createDefaultUser() }
}
```

**Available Operators:**

| Operator | Description | Return Type |
|----------|-------------|-------------|
| `map { }` | Transform parameter value | `ParamDelegate<U>` |
| `filter { }` | Filter parameter value, returns empty if condition not met | `ParamDelegate<T>` |
| `unwrapOr(default)` | Unwrap with default value if empty | `MutableState<T>` |
| `unwrapOrElse { }` | Unwrap with lazy default value if empty | `MutableState<T>` |
| `orNull()` | Get nullable value | `MutableState<T?>` |
| `asParam()` | Get Param wrapper | `MutableState<Param<T>>` |

**State Features:**
- All terminal operations (`unwrapOr`, `orNull`, etc.) return `MutableState`
- Supports Compose's `by` delegation syntax
- Value changes automatically trigger UI recomposition
- Supports both reading and writing (when using `var` delegation)

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

#### Basic Usage

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

#### Chain API for Return Results (Recommended)

Using chain API for return results provides more flexibility. Returns `MutableState` that automatically observes result changes and triggers UI recomposition.

```kotlin
@Composable
fun MainScreen() {
    // Basic usage: returns MutableState<User>
    // When target page returns result, selectedUser updates automatically, UI re-renders
    val selectedUser by Nav.result<User>(GlobalDes.SelectUser).unwrapOr(defaultUser)
    
    // Use map to transform: returns MutableState<String>
    val userName by Nav.result<User>(GlobalDes.SelectUser)
        .map { it.name }
        .unwrapOr("Not selected")
    
    // Use filter: returns MutableState<User>
    val vipUser by Nav.result<User>(GlobalDes.SelectUser)
        .filter { it.isVip }
        .unwrapOr(defaultUser)
        
    // Chain multiple operations: returns MutableState<String>
    val vipUserName by Nav.result<User>(GlobalDes.SelectUser)
        .filter { it.isVip }
        .map { it.name }
        .unwrapOr("No VIP user")
        
    // Use orNull for nullable result: returns MutableState<SelectResult?>
    val nullableResult by Nav.result<SelectResult>(GlobalDes.Selection).orNull()
    nullableResult?.let { result ->
        Text("Selected: ${result.selectedName}")
    }
    
    // Display selected user info (updates automatically with return result)
    Text("Current User: ${selectedUser.name}")
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

#### Navigation Methods

| Method | Description |
|--------|-------------|
| `Nav.to()` | Navigate to specified page |
| `Nav.back()` | Navigate back |
| `Nav.replace()` | Replace current page |
| `Nav.offAllTo()` | Clear stack and navigate |

#### Parameter Methods (Traditional API)

| Method | Description |
|--------|-------------|
| `Nav.getParams<T>()` | Get current page parameters (Composable) |
| `Nav.getResultFor<T>(destination)` | Get return result from specified page (Composable) |
| `Nav.clearCurrentData()` | Clear current page parameters (Composable) |

#### Parameter Methods (Chain API, Recommended)

| Method | Description |
|--------|-------------|
| `Nav.param<T>()` | Get parameter delegate with chain support (Composable) |
| `Nav.data<T>()` | Same as `param()`, alias method (Composable) |
| `Nav.result<T>(destination)` | Get result delegate with chain support (Composable) |

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

#### param()

```kotlin
@Composable
inline fun <reified T> Nav.param(): ParamDelegate<T>
```

Returns `ParamDelegate`, supports the following chain operations:
- `map { }`: Transform parameter value
- `filter { }`: Filter parameter value
- `unwrapOr(default)`: Unwrap with default value
- `unwrapOrElse { }`: Unwrap with lazy default value
- `orNull()`: Get nullable value
- `asParam()`: Get Param wrapper

#### result()

```kotlin
@Composable
inline fun <reified T> Nav.result(destination: Destination): ResultDelegate<T>
```

Returns `ResultDelegate`, supports the same chain operations as `ParamDelegate`.

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

