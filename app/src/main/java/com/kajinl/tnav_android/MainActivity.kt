package com.kajinl.tnav_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kajinl.tnav_android.router.NavigationScreen
import com.kajinl.tnav_android.ui.theme.Tnav_androidTheme

/**
 * 主 Activity
 * 
 * 这是应用的入口点，负责：
 * 1. 启用边缘到边缘显示
 * 2. 设置 Compose 主题
 * 3. 显示导航入口页面
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 启用边缘到边缘显示（让内容延伸到状态栏和导航栏下方）
        enableEdgeToEdge()
        
        // 设置 Compose 内容
        setContent {
            // 应用主题
            Tnav_androidTheme {
                // 显示导航入口页面（所有页面路由的入口）
                NavigationScreen()
            }
        }
    }
}