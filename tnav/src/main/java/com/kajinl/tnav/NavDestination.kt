package com.kajinl.tnav

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

/**
 * 页面导航定义抽象类
 * 所有路由目标都应该继承此类
 * 
 * @param path 路由路径
 * @param arguments 路由参数列表（默认包含 dataId 参数用于传递数据）
 */
abstract class Destination(
    path: String,
    val arguments: List<NamedNavArgument> = listOf(
        navArgument("dataId") { nullable = true }
    )
) {
    /**
     * 完整的路由字符串，格式为：path?dataId={dataId}
     */
    var route: String = "$path?dataId={dataId}"
}

