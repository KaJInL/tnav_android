package com.kajlee.tnav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

/**
 * 处理导航意图的扩展函数
 * 将 NavIntent 转换为实际的导航操作
 * 优化：减少不必要的空值检查和操作
 */
fun NavController.handleComposeNavigationIntent(intent: NavIntent) {
    when (intent) {
        is NavIntent.Back<*> -> {
            // 优化：先处理返回操作，再处理结果，减少不必要的检查
            if (intent.result != null && previousBackStackEntry != null) {
                val resultId = NavDataStore.storeData(intent.result)
                val currentRoute = currentBackStackEntry?.destination?.route
                if (currentRoute != null) {
                    // 优化：使用 indexOf 代替 split，减少字符串对象创建
                    val queryIndex = currentRoute.indexOf('?')
                    val path = if (queryIndex >= 0) currentRoute.substring(0, queryIndex) else currentRoute
                    val resultKey = "result_$path"
                    previousBackStackEntry!!.savedStateHandle.set(resultKey, resultId)
                }
            }
            // 优化：简化返回逻辑
            if (intent.route != null) {
                popBackStack(intent.route, intent.inclusive)
            } else {
                popBackStack()
            }
        }

        is NavIntent.To<*> -> {
            val route = setPath(intent.route, intent.params)
            navigate(route) {
                launchSingleTop = intent.isSingleTop
                intent.popUpToRoute?.let { popUpToRoute ->
                    popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                }
            }
        }

        is NavIntent.Replace<*> -> {
            val route = setPath(intent.route, intent.params)
            navigate(route) {
                launchSingleTop = intent.isSingleTop
                // 优化：直接弹出当前栈，不需要检查 route
                currentBackStackEntry?.destination?.route?.let {
                    popBackStack()
                }
            }
        }

        is NavIntent.OffAllTo<*> -> {
            val route = setPath(intent.route, intent.params)
            navigate(route) {
                popUpTo(0)
            }
        }
    }
}

/**
 * 使用 Destination 注册对话框路由
 */
fun NavGraphBuilder.dialogWithDestination(
    destination: Destination,
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    dialog(destination.route, destination.arguments, deepLinks, dialogProperties        ) { navBackStackEntry ->
        androidx.compose.runtime.CompositionLocalProvider(
            LocalNavBackStackEntry provides navBackStackEntry
        ) {
            // 优化：提取清理逻辑，减少重复代码
            cleanupNavDataOnDispose(navBackStackEntry)
            content()
        }
    }
}

/**
 * 使用 Destination 注册页面路由
 * 自动提供 NavBackStackEntry 到 CompositionLocal
 */
fun NavGraphBuilder.composableWithDestination(
    destination: Destination,
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(200)
        )
    },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(200)
        )
    },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    content: @Composable AnimatedContentScope.() -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition ?: enterTransition,
        popExitTransition = popExitTransition ?: exitTransition,
    ) { navBackStackEntry ->
        androidx.compose.runtime.CompositionLocalProvider(
            LocalNavBackStackEntry provides navBackStackEntry
        ) {
            // 优化：提取清理逻辑，减少重复代码
            cleanupNavDataOnDispose(navBackStackEntry)
            content()
        }
    }
}

/**
 * 优化：提取的清理逻辑，在页面销毁时自动清理参数数据
 * 减少重复代码，提升可维护性
 */
@Composable
private fun cleanupNavDataOnDispose(navBackStackEntry: NavBackStackEntry) {
    DisposableEffect(navBackStackEntry) {
        onDispose {
            // 优化：直接获取 dataId，避免多次访问 arguments
            navBackStackEntry.arguments?.getString("dataId")?.let { dataId ->
                NavDataStore.removeData(dataId)
            }
        }
    }
}

/**
 * 使用 Destination 和预设动画注册页面路由（便捷版本）
 * 
 * @param destination 目标页面
 * @param transitions 动画配置，可以使用 NavTransitions 中的预设动画
 * @param deepLinks 深度链接列表
 * @param content 页面内容
 * 
 * @example
 * ```
 * composableWithDestination(
 *     destination = GlobalDes.Detail,
 *     transitions = NavTransitions.Elastic
 * ) {
 *     DetailScreen()
 * }
 * ```
 */
fun NavGraphBuilder.composableWithDestination(
    destination: Destination,
    transitions: TransitionConfig = NavTransitions.Default,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.() -> Unit
) {
    composableWithDestination(
        destination = destination,
        deepLinks = deepLinks,
        enterTransition = transitions.enter,
        exitTransition = transitions.exit,
        popEnterTransition = transitions.popEnter,
        popExitTransition = transitions.popExit,
        content = content
    )
}

