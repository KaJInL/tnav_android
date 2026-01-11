# TNav - Android (Kotlin)

[![Maven Central](https://img.shields.io/maven-central/v/com.kajlee/tnav)](https://central.sonatype.com/artifact/com.kajlee/tnav)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

一个基于 Jetpack Compose Navigation 的 Android 导航库，提供类型安全、无序列化开销的页面导航解决方案。

## ✨ 特性

- ✅ **无序列化开销**：参数直接存储在内存中，无需 JSON 序列化/反序列化
- ✅ **类型安全**：使用 Kotlin 泛型确保类型安全
- ✅ **简洁 API**：页面无需传递 `NavBackStackEntry` 参数
- ✅ **支持任意对象**：可以传递任何 Kotlin 对象，包括 sealed class、Lambda 等
- ✅ **自动内存管理**：页面销毁时自动清理参数，无需手动管理
- ✅ **丰富的动画效果**：提供多种预设动画，支持自定义动画
- ✅ **返回结果支持**：支持页面返回时携带结果数据

## 📦 安装

在 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("com.kajlee:tnav:0.0.1")
}
```

## 🚀 快速开始

```kotlin
import com.kajinl.tnav.Destination
import com.kajinl.tnav.Nav
import com.kajinl.tnav.NavigationEffect
import com.kajinl.tnav.composableWithDestination

// 1. 定义路由
object GlobalDes {
    object Splash : Destination("Splash")
    object Main : Destination("Main")
    object Login : Destination("Login")
}

// 2. 注册路由
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

// 3. 设置导航入口
@Composable
fun NavigationScreen() {
    NavigationEffect(startDestination = GlobalDes.Splash.route) {
        registerGlobalRoute()
    }
}

// 4. 页面跳转
Nav.to(GlobalDes.Login)

// 5. 返回上一页
Nav.back()
```

## 📖 功能使用

### 基础导航

#### 简单跳转

```kotlin
// 跳转到登录页
Nav.to(GlobalDes.Login)

// 返回上一页
Nav.back()
```

#### 带参数跳转

```kotlin
// 定义参数类
data class UserInfo(
    val id: String,
    val name: String,
    val age: Int
)

// 跳转时传递参数
val userInfo = UserInfo("001", "张三", 25)
Nav.to(GlobalDes.UserDetail, params = userInfo)

// 在目标页面获取参数
@Composable
fun UserDetailScreen() {
    val userInfo = Nav.getParams<UserInfo>()
    
    if (userInfo != null) {
        Text("用户: ${userInfo.name}")
    }
}
```

#### 替换当前页面

```kotlin
// 替换当前页面（移除当前页面，导航到新页面）
// 适用于登录后跳转到主页等场景
Nav.replace(GlobalDes.Main)
```

#### 清空栈并跳转

```kotlin
// 清空所有页面，跳转到登录页
// 适用于退出登录等场景
Nav.offAllTo(GlobalDes.Login)
```

#### 返回到指定页面

```kotlin
// 返回到指定页面（不包含目标页面）
Nav.back(destination = GlobalDes.Main, inclusive = false)

// 返回到指定页面（包含目标页面）
Nav.back(destination = GlobalDes.Main, inclusive = true)
```

#### 跳转并弹出到指定页面

```kotlin
// 跳转到详情页，并弹出到首页（不包含首页）
Nav.to(
    destination = GlobalDes.Detail,
    popUpToRoute = GlobalDes.Main.route,
    inclusive = false
)

// 跳转到详情页，并弹出到首页（包含首页）
Nav.to(
    destination = GlobalDes.Detail,
    popUpToRoute = GlobalDes.Main.route,
    inclusive = true
)
```

#### 单例模式

```kotlin
// 如果栈中已存在该页面，则复用，不创建新实例
// 适用于避免重复创建相同页面的场景
Nav.to(GlobalDes.Main, isSingleTop = true)
```

### 参数传递

#### 基本对象传递

```kotlin
// 传递 data class
data class UserInfo(val id: String, val name: String)
Nav.to(GlobalDes.UserDetail, params = UserInfo("001", "张三"))

// 接收参数
@Composable
fun UserDetailScreen() {
    val userInfo = Nav.getParams<UserInfo>()
    // 使用 userInfo...
}
```

#### 复杂对象传递

```kotlin
// 传递包含 List、Map、嵌套对象的复杂数据
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

// 接收复杂对象
@Composable
fun ComplexDemoScreen() {
    val complexData = Nav.getParams<ComplexData>()
    // 直接使用，无需序列化
    complexData?.items?.forEach { item ->
        Text(item)
    }
}
```

#### Sealed Class 传递

```kotlin
// 定义 Sealed Class
sealed class PageState {
    data object Loading : PageState()
    data class Success(val message: String) : PageState()
    data class Error(val errorMessage: String) : PageState()
}

// 传递 Sealed Class
val state = PageState.Success("操作成功")
Nav.to(GlobalDes.Result, params = state)

// 接收 Sealed Class
@Composable
fun ResultScreen() {
    val state = Nav.getParams<PageState>()
    when (state) {
        is PageState.Loading -> { /* ... */ }
        is PageState.Success -> { /* ... */ }
        is PageState.Error -> { /* ... */ }
        null -> { /* 未接收到数据 */ }
    }
}
```

### 返回结果

```kotlin
// ========== 步骤1: 定义结果数据类 ==========
data class SelectResult(
    val selectedId: String,
    val selectedName: String
)

// ========== 步骤2: 在调用页面导航并获取结果 ==========
@Composable
fun MainScreen() {
    // 导航到选择页面
    Button(onClick = { Nav.to(GlobalDes.SelectionList) }) {
        Text("去选择")
    }
    
    // 获取选择页面的返回结果（自动监听变化）
    val result = Nav.getResultFor<SelectResult>(GlobalDes.SelectionList)
    
    // 显示选择的结果
    result?.let {
        Text("已选择: ${it.selectedName}")
    }
}

// ========== 步骤3: 在目标页面返回结果 ==========
@Composable
fun SelectionListScreen() {
    Card(onClick = {
        // 返回并携带结果
        val result = SelectResult("001", "选项一")
        Nav.back(result = result)
    }) {
        Text("选项一")
    }
}
```

### 动画效果

#### 使用预设动画

TNav 提供了 11 种预设动画效果，使用非常简单：

```kotlin
// 使用弹性缩放动画
composableWithDestination(
    destination = GlobalDes.Detail,
    transitions = NavTransitions.Elastic
) {
    DetailScreen()
}

// 使用淡入淡出动画
composableWithDestination(
    destination = GlobalDes.Login,
    transitions = NavTransitions.Fade
) {
    LoginScreen()
}

// 使用缩放动画
composableWithDestination(
    destination = GlobalDes.Profile,
    transitions = NavTransitions.Scale
) {
    ProfileScreen()
}
```

#### 可用的预设动画

| 动画名称 | 效果描述 | 适用场景 |
|---------|---------|---------|
| `NavTransitions.Default` | 水平滑动（默认） | 通用场景 |
| `NavTransitions.Fade` | 淡入淡出 | 轻量级过渡 |
| `NavTransitions.Scale` | 缩放+淡入淡出 | 弹窗、详情页 |
| `NavTransitions.SlideVertical` | 垂直滑动 | 底部弹窗、列表展开 |
| `NavTransitions.Elastic` | 弹性缩放 | 强调、重要页面 |
| `NavTransitions.SlideFade` | 滑动+淡入淡出 | 流畅过渡 |
| `NavTransitions.ScaleSlide` | 缩放+滑动组合 | 丰富视觉效果 |
| `NavTransitions.BottomSheet` | 从底部弹出 | 底部弹窗、抽屉 |
| `NavTransitions.RotateScale` | 旋转+缩放 | 特殊效果页面 |
| `NavTransitions.QuickFade` | 快速淡入淡出 | 快速切换 |
| `NavTransitions.None` | 无动画 | 性能优化场景 |

### 对话框导航

```kotlin
// 注册对话框路由
fun NavGraphBuilder.registerDialogRoute() {
    dialogWithDestination(GlobalDes.ConfirmDialog) {
        ConfirmDialogScreen()
    }
}

// 打开对话框
Nav.to(GlobalDes.ConfirmDialog)

// 关闭对话框
Nav.back()
```

## 🔧 定制化

### 自定义动画

如果需要完全自定义动画，可以使用原始 API：

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

### 创建自定义动画配置

你也可以创建自己的动画配置：

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

// 使用自定义动画
composableWithDestination(
    destination = GlobalDes.Detail,
    transitions = myCustomTransition
) {
    DetailScreen()
}
```

### 深链接支持

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

## 📚 API 参考

### Nav 对象

| 方法 | 说明 |
|------|------|
| `Nav.to()` | 导航到指定页面 |
| `Nav.back()` | 返回上一页 |
| `Nav.replace()` | 替换当前页面 |
| `Nav.offAllTo()` | 清空栈并跳转 |
| `Nav.getParams<T>()` | 获取当前页面参数（Composable） |
| `Nav.getResultFor<T>(destination)` | 获取指定页面的返回结果（Composable） |
| `Nav.clearCurrentData()` | 清理当前页面参数（Composable） |

### NavGraphBuilder 扩展

| 函数 | 说明 |
|------|------|
| `composableWithDestination()` | 注册页面路由 |
| `dialogWithDestination()` | 注册对话框路由 |

### 完整 API 签名

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

## ❓ 常见问题

### Q: 为什么不需要序列化？

A: 参数直接存储在内存的 `ConcurrentHashMap` 中，通过 UUID 作为 key 关联。这种方式避免了序列化开销，同时支持传递任意 Kotlin 对象。

### Q: 内存会泄漏吗？

A: 不会。系统会在页面销毁时自动清理导航参数，无需手动管理。当页面从导航栈中移除时，对应的数据会自动从内存中删除。

### Q: 可以传递 Lambda 吗？

A: 可以！由于数据存储在内存中，可以传递任何可以在内存中持有的对象，包括 Lambda、回调等。

### Q: 支持深链接吗？

A: 支持。在 `composableWithDestination` 中传入 `deepLinks` 参数即可。

## 🔗 相关链接

- **Maven Central**: https://central.sonatype.com/artifact/com.kajlee/tnav
- **GitHub**: https://github.com/KaJInL/tnav

## 📄 许可证

Apache License 2.0

**重要提示**：使用本库时，**必须**在您的项目中注明使用了 TNav 库，并保留原始版权声明。您可以在以下位置添加说明：

- 项目的 README.md 文件中
- 应用的"关于"页面
- 开源项目的 LICENSE 文件中

示例：

```
本项目使用了 TNav 导航库
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
