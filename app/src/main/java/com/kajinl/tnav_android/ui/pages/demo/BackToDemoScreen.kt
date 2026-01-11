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
 * 返回到指定页面演示
 * 
 * 演示如何使用 Nav.back() 返回到指定页面。
 * 
 * 特点：
 * - 可以返回到导航栈中的任意页面
 * - 可以设置是否包含目标页面（inclusive）
 * - 适用于"返回到首页"等场景
 * 
 * 使用方式：
 * Nav.back(destination = target, inclusive = false)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackToDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("返回到指定页面演示") },
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
                text = "返回到指定页面",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮会跳转到中间页，然后演示返回到指定页面",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 演示：跳转到中间页
            Button(
                onClick = {
                    Nav.to(DemoDes.BackToPage1)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("跳转到中间页1")
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
                            // 返回到指定页面（不包含目标页面）
                            Nav.back(destination = DemoDes.BackToDemo, inclusive = false)
                            
                            // 返回到指定页面（包含目标页面）
                            Nav.back(destination = DemoDes.BackToDemo, inclusive = true)
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * 中间页1
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackToPage1Screen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("中间页1") },
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
                text = "中间页1",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "继续跳转到中间页2",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    Nav.to(DemoDes.BackToPage2)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("跳转到中间页2")
            }
        }
    }
}

/**
 * 中间页2
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackToPage2Screen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("中间页2") },
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
                text = "中间页2",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "现在可以返回到指定页面",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 返回到指定页面（不包含目标页面）
            Button(
                onClick = {
                    Nav.back(destination = DemoDes.BackToDemo, inclusive = false)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("返回到演示页（不包含）")
            }
            
            // 返回到指定页面（包含目标页面）
            Button(
                onClick = {
                    Nav.back(destination = DemoDes.BackToDemo, inclusive = true)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("返回到演示页（包含）")
            }
        }
    }
}

