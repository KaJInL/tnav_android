package com.kajinl.tnav_android.ui.pages.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kajinl.tnav.Nav
import com.kajinl.tnav_android.router.des.DemoDes

/**
 * 演示项数据类
 * 
 * @param title 演示标题
 * @param description 演示描述
 * @param category 分类
 */
data class DemoItem(
    val title: String,
    val description: String,
    val category: String = ""
)

/**
 * 演示列表页面
 * 
 * 这是所有 TNav 功能演示的入口页面。
 * 点击不同的演示项可以跳转到对应的演示页面，体验 TNav 的各种功能。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoListScreen() {
    // 定义演示项列表，按功能分类
    val demoItems = listOf(
        // 基础导航
        DemoItem("简单导航", "不带参数的页面跳转", "基础导航"),
        DemoItem("带参数导航", "传递基本对象到详情页", "基础导航"),
        DemoItem("替换页面", "使用 replace() 替换当前页面", "基础导航"),
        DemoItem("清空栈跳转", "使用 offAllTo() 清空所有页面", "基础导航"),
        DemoItem("返回到指定页面", "使用 back() 返回到指定页面", "基础导航"),
        DemoItem("单例模式", "使用 isSingleTop 避免重复创建", "基础导航"),
        
        // 参数传递
        DemoItem("复杂对象传递", "传递 List、Map、嵌套对象", "参数传递"),
        DemoItem("Sealed Class 传递", "传递 sealed class 类型", "参数传递"),
        
        // 返回结果
        DemoItem("返回结果", "页面返回时携带结果数据", "返回结果"),
        
        // 动画效果
        DemoItem("动画效果", "体验所有预设动画效果", "动画效果"),
        
        // 对话框
        DemoItem("对话框导航", "使用对话框进行页面跳转", "对话框"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TNav 功能演示") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            var lastCategory = ""
            itemsIndexed(demoItems) { index, item ->
                // 显示分类标题（当分类改变时）
                if (item.category != lastCategory) {
                    if (lastCategory.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    lastCategory = item.category
                }
                
                // 显示演示项
                Card(
                    onClick = {
                        // 根据不同的演示项跳转到对应的页面
                        when (item.title) {
                            "简单导航" -> Nav.to(DemoDes.SimpleDemo)
                            "带参数导航" -> Nav.to(DemoDes.ParamsDemo)
                            "替换页面" -> Nav.to(DemoDes.ReplaceDemo)
                            "清空栈跳转" -> Nav.to(DemoDes.OffAllDemo)
                            "返回到指定页面" -> Nav.to(DemoDes.BackToDemo)
                            "单例模式" -> Nav.to(DemoDes.SingleTopDemo)
                            "复杂对象传递" -> Nav.to(DemoDes.ComplexDataDemo)
                            "Sealed Class 传递" -> Nav.to(DemoDes.SealedClassDemo)
                            "返回结果" -> Nav.to(DemoDes.ResultDemo)
                            "动画效果" -> Nav.to(DemoDes.TransitionsDemo)
                            "对话框导航" -> Nav.to(DemoDes.DialogDemo)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

/**
 * 用户信息数据类
 * 
 * 用于演示参数传递功能。
 * TNav 支持传递任意 Kotlin 对象，包括 data class、sealed class 等。
 */
data class UserInfo(
    val id: String,
    val name: String,
    val age: Int,
    val email: String
)
