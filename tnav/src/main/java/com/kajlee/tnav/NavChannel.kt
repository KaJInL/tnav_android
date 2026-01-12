package com.kajlee.tnav

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * 导航通道内部对象
 * 用于在协程间传递导航意图
 */
internal object NavChannel {

    // 使用合理的有限容量，避免内存占用过大
    // 64 个导航意图的缓冲区足够处理大多数场景
    private val channel = Channel<NavIntent>(
        capacity = 64,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    internal val navChannel = channel.receiveAsFlow()

    internal fun navigate(destination: NavIntent) {
        channel.trySend(destination)
    }
}

