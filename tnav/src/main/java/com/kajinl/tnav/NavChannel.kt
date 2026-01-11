package com.kajinl.tnav

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * 导航通道内部对象
 * 用于在协程间传递导航意图
 */
internal object NavChannel {

    private val channel = Channel<NavIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    internal val navChannel = channel.receiveAsFlow()

    internal fun navigate(destination: NavIntent) {
        channel.trySend(destination)
    }
}

