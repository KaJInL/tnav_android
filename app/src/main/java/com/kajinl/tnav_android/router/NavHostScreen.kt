package com.kajinl.tnav_android.router

import androidx.compose.runtime.Composable
import com.kajinl.tnav.NavigationEffect
import com.kajinl.tnav_android.router.des.DemoDes
import com.kajinl.tnav_android.router.des.registerDemoRoute
import com.kajinl.tnav_android.router.des.registerGlobalRoute

/**
 * 导航入口页面
 * 
 * 这是整个应用的导航入口，使用 TNav 的 NavigationEffect 来管理所有页面路由。
 * 
 * 工作流程：
 * 1. 设置起始页面为演示列表页
 * 2. 注册所有页面路由（演示路由、全局路由等）
 * 3. TNav 会自动处理页面跳转、参数传递、返回结果等
 */
@Composable
fun NavigationScreen() {
    // 使用 TNav 的 NavigationEffect 来管理导航
    // startDestination: 设置起始页面（应用启动时显示的页面）
    NavigationEffect(startDestination = DemoDes.DemoList.route) {
        // 注册演示页面路由（包含各种 TNav 功能演示）
        registerDemoRoute()
        
        // 注册全局页面路由（启动页、主页面等）
        registerGlobalRoute()
    }
}

