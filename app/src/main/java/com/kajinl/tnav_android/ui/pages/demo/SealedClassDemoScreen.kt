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
 * Sealed Class 传递演示
 * 
 * 演示如何传递 Sealed Class 类型。
 * 
 * TNav 支持传递 Sealed Class，非常适合状态管理场景。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SealedClassDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sealed Class 传递演示") },
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
                text = "Sealed Class 传递",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮传递不同的 Sealed Class 状态",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 传递 Loading 状态
            Button(
                onClick = {
                    Nav.to(DemoDes.SealedClassResult, params = PageState.Loading)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("传递 Loading 状态")
            }
            
            // 传递 Success 状态
            Button(
                onClick = {
                    Nav.to(DemoDes.SealedClassResult, params = PageState.Success("操作成功！"))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("传递 Success 状态")
            }
            
            // 传递 Error 状态
            Button(
                onClick = {
                    Nav.to(DemoDes.SealedClassResult, params = PageState.Error("操作失败"))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("传递 Error 状态")
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
                            sealed class PageState {
                                object Loading : PageState()
                                data class Success(val message: String) : PageState()
                                data class Error(val errorMessage: String) : PageState()
                            }
                            
                            Nav.to(DemoDes.SealedClassResult, params = PageState.Success("成功"))
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * Sealed Class 结果页
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SealedClassResultScreen() {
    // 获取传递的 Sealed Class
    val pageState = Nav.getParams<PageState>()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sealed Class 结果") },
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
            when (pageState) {
                is PageState.Loading -> {
                    Text(
                        text = "加载中...",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                is PageState.Success -> {
                    Text(
                        text = "成功",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pageState.message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                is PageState.Error -> {
                    Text(
                        text = "错误",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pageState.errorMessage,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                null -> {
                    Text(
                        text = "未接收到数据",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

/**
 * 页面状态 Sealed Class
 */
sealed class PageState {
    data object Loading : PageState()
    data class Success(val message: String) : PageState()
    data class Error(val errorMessage: String) : PageState()
}

