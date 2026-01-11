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
 * 复杂对象传递演示
 * 
 * 演示如何传递复杂对象（包含 List、Map、嵌套对象等）。
 * 
 * TNav 的优势：
 * - 无需序列化，直接传递 Kotlin 对象
 * - 支持任意复杂的数据结构
 * - 类型安全，使用泛型确保类型正确
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplexDataDemoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("复杂对象传递演示") },
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
                text = "复杂对象传递",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "点击按钮传递包含 List、Map、嵌套对象的复杂数据",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    // 创建复杂对象
                    val complexData = ComplexData(
                        items = listOf("项目1", "项目2", "项目3"),
                        metadata = mapOf(
                            "key1" to "value1",
                            "key2" to "value2",
                            "key3" to "value3"
                        ),
                        config = Config(
                            enabled = true,
                            timeout = 3000
                        )
                    )
                    // 传递复杂对象
                    Nav.to(DemoDes.ComplexDataDetail, params = complexData)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("传递复杂对象")
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
                            data class ComplexData(
                                val items: List<String>,
                                val metadata: Map<String, String>,
                                val config: Config
                            )
                            
                            val complexData = ComplexData(...)
                            Nav.to(DemoDes.ComplexDataDetail, params = complexData)
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/**
 * 复杂对象详情页
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplexDataDetailScreen() {
    // 获取传递的复杂对象
    val complexData = Nav.getParams<ComplexData>()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("复杂对象详情") },
                navigationIcon = {
                    IconButton(onClick = { Nav.back() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                }
            )
        }
    ) { padding ->
        if (complexData != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "接收到的复杂对象",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Divider()
                
                // 显示列表数据
                Text(
                    text = "列表数据：",
                    style = MaterialTheme.typography.titleMedium
                )
                complexData.items.forEach { item ->
                    Text(
                        text = "  • $item",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // 显示 Map 数据
                Text(
                    text = "Map 数据：",
                    style = MaterialTheme.typography.titleMedium
                )
                complexData.metadata.forEach { (key, value) ->
                    Text(
                        text = "  $key: $value",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // 显示嵌套对象
                Text(
                    text = "嵌套对象：",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "  启用: ${complexData.config.enabled}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "  超时: ${complexData.config.timeout}ms",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("未找到数据")
            }
        }
    }
}

/**
 * 复杂数据类
 */
data class ComplexData(
    val items: List<String>,
    val metadata: Map<String, String>,
    val config: Config
)

/**
 * 配置数据类
 */
data class Config(
    val enabled: Boolean,
    val timeout: Long
)

