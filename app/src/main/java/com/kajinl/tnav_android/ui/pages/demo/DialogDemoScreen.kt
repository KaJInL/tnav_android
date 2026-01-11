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
import com.kajinl.tnav_android.router.des.DemoDes

/**
 * 对话框演示
 * 
 * 演示如何使用 dialogWithDestination() 注册对话框路由。
 * 
 * 特点：
 * - 对话框形式的页面跳转
 * - 支持参数传递
 * - 支持返回结果
 * - 使用方式与普通页面相同
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("对话框演示") },
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
                text = "对话框导航",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮打开对话框",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    // 打开对话框
                    Nav.to(DemoDes.DialogContent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("打开对话框")
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
                        text = """
                            // 注册对话框路由
                            dialogWithDestination(DemoDes.DialogContent) {
                                DialogContentScreen()
                            }
                            
                            // 打开对话框
                            Nav.to(DemoDes.DialogContent)
                            
                            // 关闭对话框
                            Nav.back()
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * 对话框内容页
 */
@Composable
fun DialogContentScreen() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "这是对话框内容",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Text(
                text = "对话框支持所有 TNav 功能，包括参数传递、返回结果等",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Button(
                onClick = {
                    // 关闭对话框
                    Nav.back()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("关闭")
            }
        }
    }
}

