package com.kajlee.tnav

/**
 * 导航意图 sealed class
 * 用于封装不同类型的导航操作
 */
sealed class NavIntent() {

    /**
     * 返回堆栈弹出到指定目标
     * @property route 指定目标
     * @property inclusive 是否弹出指定目标
     * @constructor
     * 【"4"、"3"、"2"、"1"】 Back("2",true)->【"4"、"3"】
     * 【"4"、"3"、"2"、"1"】 Back("2",false)->【"4"、"3"、"2"】
     */
    data class Back<T>(
        val route: String? = null,
        val inclusive: Boolean = false,
        val result: T?
    ) : NavIntent()


    /**
     * 导航到指定目标
     * @property route 指定目标
     * @property popUpToRoute 返回堆栈弹出到指定目标
     * @property inclusive 是否弹出指定popUpToRoute目标
     * @property isSingleTop 是否是栈中单实例模式
     * @constructor
     */
    data class To<T>(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
        val params: T?
    ) : NavIntent()

    /**
     * 替换当前导航/弹出当前导航并导航到指定目的地
     * @property route 当前导航
     * @property isSingleTop 是否是栈中单实例模式
     * @constructor
     */
    data class Replace<T>(
        val route: String,
        val isSingleTop: Boolean = false,
        val params: T?
    ) : NavIntent()

    /**
     * 清空导航栈并导航到指定目的地
     * @property route 指定目的地
     * @constructor
     */
    data class OffAllTo<T>(
        val route: String,
        val params: T?
    ) : NavIntent()

}

