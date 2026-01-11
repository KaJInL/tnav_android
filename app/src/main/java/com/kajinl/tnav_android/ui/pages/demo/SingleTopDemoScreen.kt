package com.kajinl.tnav_android.ui.pages.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kajinl.tnav.Nav
import com.kajinl.tnav_android.router.des.DemoDes

/**
 * 单例模式演示
 * 
 * 演示如何使用 isSingleTop 参数实现单例模式。
 * 
 * 特点：
 * - 如果栈中已存在该页面，则复用，不创建新实例
 * - 适用于避免重复创建相同页面的场景
 * - 例如：从多个页面跳转到首页，只保留一个首页实例
 * 
 * 使用方式：
 * Nav.to(destination, isSingleTop = true)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleTopDemoScreen() {
    var clickCount by remember { mutableIntStateOf(0) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("单例模式演示") },
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
                text = "单例模式",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击次数：$clickCount",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = "点击按钮会跳转到自己，但不会创建新实例（isSingleTop = true）",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 演示：单例模式跳转
            Button(
                onClick = {
                    clickCount++
                    // 使用 isSingleTop = true，如果栈中已存在该页面，则复用
                    Nav.to(DemoDes.SingleTopDemo, isSingleTop = true)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("跳转到自己（单例模式）")
            }
            
            // 对比：普通跳转
            Button(
                onClick = {
                    clickCount++
                    // 不使用 isSingleTop，每次都会创建新实例
                    Nav.to(DemoDes.SingleTopDemo, isSingleTop = false)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("跳转到自己（普通模式）")
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
                            // 单例模式：如果栈中已存在，则复用
                            Nav.to(DemoDes.SingleTopDemo, isSingleTop = true)
                            
                            // 普通模式：每次都会创建新实例
                            Nav.to(DemoDes.SingleTopDemo, isSingleTop = false)
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

