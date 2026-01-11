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
 * 带参数导航演示页面
 * 
 * 演示如何使用 Nav.to() 传递参数到目标页面。
 * 
 * 使用方式：
 * Nav.to(destination, params = 数据对象)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParamsDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("带参数导航演示") },
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
                text = "带参数导航",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮跳转到详情页，并传递用户信息",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 演示：传递用户信息对象
            Button(
                onClick = {
                    // 创建用户信息对象
                    val userInfo = UserInfo(
                        id = "001",
                        name = "张三",
                        age = 25,
                        email = "zhangsan@example.com"
                    )
                    // 使用 params 参数传递数据
                    Nav.to(DemoDes.UserDetail, params = userInfo)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("跳转到详情页（带参数）")
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
                            val userInfo = UserInfo(
                                id = "001",
                                name = "张三",
                                age = 25,
                                email = "zhangsan@example.com"
                            )
                            Nav.to(DemoDes.UserDetail, params = userInfo)
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

