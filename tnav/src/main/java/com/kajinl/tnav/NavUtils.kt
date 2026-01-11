package com.kajinl.tnav

/**
 * 设置路由路径，将数据存储到内存并返回包含 dataId 的路由
 * 
 * @param route 原始路由路径
 * @param data 要传递的数据（可为 null）
 * @return 包含 dataId 的完整路由路径
 */
fun setPath(route: String, data: Any?): String {
    if (data == null) {
        return route.replace("{dataId}", "")
    }
    val dataId = NavDataStore.storeData(data)
    return route.replace("{dataId}", dataId)
}

