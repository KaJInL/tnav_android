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
 * 替换页面演示
 * 
 * 演示如何使用 Nav.replace() 替换当前页面。
 * 
 * 特点：
 * - 当前页面会被移除，不会保留在导航栈中
 * - 用户按返回键不会回到被替换的页面
 * - 适用于登录后跳转到主页等场景
 * 
 * 使用方式：
 * Nav.replace(destination)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplaceDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("替换页面演示") },
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
                text = "替换页面",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮会替换当前页面，当前页面不会保留在导航栈中",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 演示：替换当前页面
            Button(
                onClick = {
                    // 替换当前页面，当前页面会被移除
                    Nav.replace(DemoDes.ReplaceTarget)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("替换当前页面")
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
                        text = "Nav.replace(DemoDes.ReplaceTarget)",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * 替换目标页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplaceTargetScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("替换后的页面") },
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
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "这是替换后的页面",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "按返回键会返回到演示列表页，不会回到被替换的页面",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

