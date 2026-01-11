package com.kajinl.tnav

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavBackStackEntry

/**
 * 提供当前导航栈条目的 CompositionLocal
 * 用于在 Composable 函数中自动获取当前页面的 NavBackStackEntry
 */
val LocalNavBackStackEntry = compositionLocalOf<NavBackStackEntry?> { null }

