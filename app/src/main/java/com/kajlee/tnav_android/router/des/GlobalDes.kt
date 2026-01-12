package com.kajlee.tnav_android.router.des

import androidx.navigation.NavGraphBuilder
import com.kajlee.tnav.Destination
import com.kajlee.tnav.composableWithDestination
import com.kajlee.tnav_android.ui.pages.global.main.MainScreen
import com.kajlee.tnav_android.ui.pages.global.splash.SplashScreen

/**
 * 全局页面路由定义
 * 
 * 这里定义应用中的全局页面路由，包括启动页、主页面等。
 * 每个路由都是一个 Destination 对象，用于页面跳转。
 */
object GlobalDes {
    /** 启动页（开屏页） */
    object Splash : Destination("Splash")

    /** 主页面 */
    object Main : Destination("Main")
}

/**
 * 注册全局页面路由
 * 
 * 使用 TNav 的 composableWithDestination 函数来注册页面路由。
 * 注册后，就可以使用 Nav.to(GlobalDes.Splash) 等方式进行页面跳转。
 */
fun NavGraphBuilder.registerGlobalRoute() {
    // 注册启动页路由
    composableWithDestination(GlobalDes.Splash) {
        SplashScreen()
    }
    
    // 注册主页面路由
    composableWithDestination(GlobalDes.Main) {
        MainScreen()
    }
}

