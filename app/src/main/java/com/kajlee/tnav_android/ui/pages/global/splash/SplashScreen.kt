package com.kajlee.tnav_android.ui.pages.global.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kajlee.tnav.Nav
import com.kajlee.tnav_android.router.des.GlobalDes
import kotlinx.coroutines.delay

/**
 * 启动页（开屏页）
 * 
 * 功能说明：
 * 1. 显示应用 Logo 或欢迎信息
 * 2. 延迟 2 秒后自动跳转到主页面
 * 3. 使用 Nav.replace() 替换当前页面（不会保留在导航栈中）
 */
@Composable
fun SplashScreen() {
    // 使用 LaunchedEffect 在页面显示时执行一次
    LaunchedEffect(Unit) {
        // 延迟 2 秒
        delay(2000)
        
        // 使用 replace 替换当前页面（启动页不会保留在导航栈中）
        // 这样用户按返回键时不会回到启动页
        Nav.replace(GlobalDes.Main)
    }
    
    // 显示启动页内容
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TNav 演示",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

