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
 * 返回结果演示
 * 
 * 演示如何使用 Nav.getResultFor() 获取页面返回的结果。
 * 
 * 使用流程：
 * 1. 跳转到目标页面
 * 2. 在目标页面使用 Nav.back(result = 结果) 返回并携带结果
 * 3. 在调用页面使用 Nav.getResultFor<T>(destination) 获取结果
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultDemoScreen() {
    // 获取选择列表页的返回结果
    val result = Nav.getResultFor<SelectResult>(DemoDes.SelectionList)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("返回结果演示") },
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
                text = "返回结果",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮跳转到选择页面，选择后返回结果",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    // 跳转到选择列表页
                    Nav.to(DemoDes.SelectionList)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("跳转到选择页面")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 显示返回结果
            if (result != null) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "选择的结果：",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "ID: ${result.selectedId}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "名称: ${result.selectedName}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                Text(
                    text = "暂无选择结果",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
                            // 1. 跳转到目标页面
                            Nav.to(DemoDes.SelectionList)
                            
                            // 2. 获取返回结果（自动监听变化）
                            val result = Nav.getResultFor<SelectResult>(DemoDes.SelectionList)
                            
                            // 3. 在目标页面返回结果
                            Nav.back(result = SelectResult("001", "选项一"))
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * 选择结果数据类
 */
data class SelectResult(
    val selectedId: String,
    val selectedName: String
)

/**
 * 选择列表页
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionListScreen() {
    val items = listOf(
        "选项一",
        "选项二",
        "选项三",
        "选项四",
        "选项五"
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("选择列表") },
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
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    onClick = {
                        // 返回并携带结果
                        val result = SelectResult(
                            selectedId = "00${index + 1}",
                            selectedName = item
                        )
                        Nav.back(result = result)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

