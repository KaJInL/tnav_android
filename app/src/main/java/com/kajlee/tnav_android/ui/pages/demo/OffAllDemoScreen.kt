package com.kajlee.tnav_android.ui.pages.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kajlee.tnav.Nav
import com.kajlee.tnav_android.router.des.DemoDes

/**
 * 清空栈跳转演示
 * 
 * 演示如何使用 Nav.offAllTo() 清空所有页面并跳转到指定页面。
 * 
 * 特点：
 * - 清空导航栈中的所有页面
 * - 跳转到指定页面
 * - 用户按返回键会退出应用
 * - 适用于退出登录、跳转到登录页等场景
 * 
 * 使用方式：
 * Nav.offAllTo(destination)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffAllDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("清空栈跳转演示") },
                navigationIcon = {
                    IconButton(onClick = { Nav.back() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "清空栈跳转",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮会清空所有页面并跳转到目标页面",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 演示：清空栈并跳转
            Button(
                onClick = {
                    // 清空所有页面并跳转到目标页面
                    Nav.offAllTo(DemoDes.OffAllTarget)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("清空栈并跳转")
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 代码示例
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "代码示例：",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Nav.offAllTo(DemoDes.OffAllTarget)",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * 清空栈目标页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffAllTargetScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("清空栈后的页面") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "这是清空栈后的页面",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "导航栈已被清空，按返回键会退出应用",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    // 返回到演示列表
                    Nav.offAllTo(DemoDes.DemoList)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("返回演示列表")
            }
        }
    }
}

