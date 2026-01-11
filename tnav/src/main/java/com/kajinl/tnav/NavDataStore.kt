package com.kajinl.tnav

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * 内存数据存储对象
 * 用于存储页面跳转时传递的参数，避免序列化开销
 */
object NavDataStore {
    private val dataStore = ConcurrentHashMap<String, Any>()

    /**
     * 存储数据并返回唯一ID
     */
    fun <T> storeData(data: T): String {
        val dataId = UUID.randomUUID().toString()
        dataStore[dataId] = data as Any
        return dataId
    }

    /**
     * 根据ID获取数据
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getData(dataId: String): T? {
        return dataStore[dataId] as? T
    }

    /**
     * 移除指定ID的数据
     */
    fun removeData(dataId: String) {
        dataStore.remove(dataId)
    }

    /**
     * 清空所有数据
     */
    fun clear() {
        dataStore.clear()
    }
}

