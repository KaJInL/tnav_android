package com.kajinl.tnav_android.ui.pages.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kajinl.tnav.Nav

/**
 * 用户详情页面
 * 
 * 这个页面演示了带参数传递的导航功能：
 * 1. 从上一个页面传递 UserInfo 对象
 * 2. 使用 Nav.getParams<UserInfo>() 获取传递的参数
 * 3. 显示用户信息
 * 
 * TNav 的优势：
 * - 无需序列化，直接传递 Kotlin 对象
 * - 类型安全，使用泛型确保类型正确
 * - 自动内存管理，页面销毁时自动清理参数
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen() {
    // 使用 Nav.getParams() 获取传递的参数
    // 这是 TNav 的核心功能：从内存中直接获取对象，无需序列化
    val userInfo = Nav.getParams<UserInfo>()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("用户详情") },
                navigationIcon = {
                    IconButton(onClick = { Nav.back() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                }
            )
        }
    ) { padding ->
        // 如果获取到了参数，显示用户信息
        if (userInfo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "用户信息",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Divider()
                
                // 显示用户信息
                InfoRow(label = "用户ID", value = userInfo.id)
                InfoRow(label = "姓名", value = userInfo.name)
                InfoRow(label = "年龄", value = userInfo.age.toString())
                InfoRow(label = "邮箱", value = userInfo.email)
                
                Spacer(modifier = Modifier.weight(1f))
                
                Button(
                    onClick = { Nav.back() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("返回")
                }
            }
        } else {
            // 如果没有获取到参数，显示错误提示
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("未找到用户信息")
            }
        }
    }
}

/**
 * 信息行组件
 * 
 * 用于显示标签和值的组合，例如"用户ID: 001"
 */
@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

