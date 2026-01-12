package com.kajlee.tnav

/**
 * 设置路由路径，将数据存储到内存并返回包含 dataId 的路由
 * 优化：提前检查是否需要替换，避免不必要的字符串操作
 * 
 * @param route 原始路由路径
 * @param data 要传递的数据（可为 null）
 * @return 包含 dataId 的完整路由路径
 */
fun setPath(route: String, data: Any?): String {
    // 优化：如果 route 中没有 {dataId} 占位符，直接返回
    if (!route.contains("{dataId}")) {
        return route
    }
    if (data == null) {
        return route.replace("{dataId}", "")
    }
    val dataId = NavDataStore.storeData(data)
    return route.replace("{dataId}", dataId)
}

