package com.kajinl.tnav

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/**
 * 导航主对象
 * 提供所有导航相关的 API
 */
object Nav {
    internal fun navigate(destination: NavIntent) {
        NavChannel.navigate(destination)
    }

    /**
     * 返回上一页
     * @param destination 返回到指定目标（可选）
     * @param inclusive 是否包含指定目标（默认 false）
     * @param result 返回时携带的结果（可选）
     */
    fun back(
        destination: Destination? = null,
        inclusive: Boolean = false,
        result: Any? = null
    ) {
        navigate(
            NavIntent.Back(destination?.route, inclusive, result)
        )
    }

    /**
     * 导航到指定页面
     * @param destination 目标页面
     * @param popUpToRoute 返回到指定路由（可选）
     * @param inclusive 是否包含 popUpToRoute（默认 false）
     * @param isSingleTop 是否单例模式（默认 false）
     * @param params 传递的参数（可选）
     */
    fun to(
        destination: Destination,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
        params: Any? = null
    ) {
        navigate(
            NavIntent.To(
                destination.route, popUpToRoute, inclusive, isSingleTop, params
            )
        )
    }

    /**
     * 获取指定页面的返回结果（Composable 版本）
     * 会自动监听结果变化，当页面返回时自动更新
     * 
     * @param destination 目标页面，用于确定从哪个页面获取结果
     * @return 返回结果，如果不存在则返回 null
     */
    @Composable
    fun <T> getResultFor(destination: Destination): T? {
        val navBackStackEntry = LocalNavBackStackEntry.current ?: return null
        // 使用 destination 的 path（去掉参数部分）作为 key 来获取结果
        val path = destination.route.split("?")[0]
        val resultKey = "result_$path"
        
        // 使用 StateFlow 监听结果变化，并转换为 State
        val resultId by navBackStackEntry.savedStateHandle.getStateFlow<String?>(resultKey, null)
            .collectAsState()
        
        return remember(resultId) {
            resultId?.let { NavDataStore.getData<T>(it) }
        }
    }

    /**
     * 替换当前页面
     * @param destination 目标页面
     * @param isSingleTop 是否单例模式（默认 false）
     * @param params 传递的参数（可选）
     */
    fun replace(
        destination: Destination,
        isSingleTop: Boolean = false,
        params: Any? = null
    ) {
        navigate(
            NavIntent.Replace(
                destination.route, isSingleTop, params
            )
        )
    }

    /**
     * 清空导航栈并导航到指定页面
     * @param destination 目标页面
     * @param params 传递的参数（可选）
     */
    fun offAllTo(
        destination: Destination,
        params: Any? = null
    ) {
        navigate(NavIntent.OffAllTo(destination.route, params))
    }

    /**
     * 从内存中获取导航参数（需要 NavBackStackEntry）
     * 通常用于非 Composable 函数
     */
    fun <T> getData(navBackStackEntry: NavBackStackEntry): T? {
        val dataId = navBackStackEntry.arguments?.getString("dataId")
        return if (dataId != null) {
            NavDataStore.getData(dataId)
        } else {
            null
        }
    }

    /**
     * 从内存中获取导航参数（Composable 版本）
     * 自动获取当前页面的 NavBackStackEntry
     */
    @Composable
    fun <T> getParams(): T? {
        val navBackStackEntry = LocalNavBackStackEntry.current
        return if (navBackStackEntry != null) {
            getData(navBackStackEntry)
        } else {
            null
        }
    }

    /**
     * 清理导航参数（可选，用于释放内存）
     * 通常用于非 Composable 函数
     */
    fun clearData(navBackStackEntry: NavBackStackEntry) {
        val dataId = navBackStackEntry.arguments?.getString("dataId")
        if (dataId != null) {
            NavDataStore.removeData(dataId)
        }
    }

    /**
     * 清理当前页面的导航参数（Composable 版本）
     */
    @Composable
    fun clearCurrentData() {
        val navBackStackEntry = LocalNavBackStackEntry.current
        if (navBackStackEntry != null) {
            clearData(navBackStackEntry)
        }
    }
}

/**
 * 封装导航控件
 * 自动处理导航意图并渲染 NavHost
 * 
 * @param navController NavHostController（可选，默认创建新的）
 * @param startDestination 起始路由
 * @param builder 路由构建器
 */
@SuppressLint("ContextCastToActivity")
@Composable
fun NavigationEffect(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    builder: androidx.navigation.NavGraphBuilder.() -> Unit,
) {
    val activity = (LocalContext.current as? Activity)
    val navChannel = NavChannel.navChannel
    LaunchedEffect(activity, navController, navChannel) {
        navChannel.collect {
            if (activity?.isFinishing == true) {
                return@collect
            }
            navController.handleComposeNavigationIntent(it)
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = builder
    )
}

