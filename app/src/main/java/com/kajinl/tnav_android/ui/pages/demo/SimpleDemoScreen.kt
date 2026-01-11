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
 * 简单导航演示页面
 * 
 * 这个页面演示了最简单的导航功能：
 * 1. 不带任何参数的页面跳转
 * 2. 使用 Nav.back() 返回上一页
 * 
 * 这是 TNav 最基础的使用方式。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("简单导航演示") },
                navigationIcon = {
                    // 返回按钮，点击后返回上一页
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
                text = "这是一个简单的页面",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "没有携带任何参数",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 返回按钮
            Button(onClick = { Nav.back() }) {
                Text("返回")
            }
        }
    }
}

