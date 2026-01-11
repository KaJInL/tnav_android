package com.kajinl.tnav_android.ui.pages.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kajinl.tnav.Nav
import com.kajinl.tnav_android.router.des.DemoDes

/**
 * 动画效果演示列表
 * 
 * 展示所有可用的预设动画效果。
 * 每个动画效果都有独立的路由，在 DemoDes 中配置了对应的动画。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitionsDemoScreen() {
    val transitions = listOf(
        "Default" to ("水平滑动（默认）" to DemoDes.TransitionDefault),
        "Fade" to ("淡入淡出" to DemoDes.TransitionFade),
        "Scale" to ("缩放+淡入淡出" to DemoDes.TransitionScale),
        "SlideVertical" to ("垂直滑动" to DemoDes.TransitionSlideVertical),
        "Elastic" to ("弹性缩放" to DemoDes.TransitionElastic),
        "SlideFade" to ("滑动+淡入淡出" to DemoDes.TransitionSlideFade),
        "ScaleSlide" to ("缩放+滑动组合" to DemoDes.TransitionScaleSlide),
        "BottomSheet" to ("从底部弹出" to DemoDes.TransitionBottomSheet),
        "RotateScale" to ("旋转+缩放" to DemoDes.TransitionRotateScale),
        "QuickFade" to ("快速淡入淡出" to DemoDes.TransitionQuickFade),
        "None" to ("无动画" to DemoDes.TransitionNone)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("动画效果演示") },
                navigationIcon = {
                    IconButton(onClick = { Nav.back() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                }
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
            items(transitions) { (name, pair) ->
                val (description, destination) = pair
                Card(
                    onClick = {
                        // 跳转到对应的动画展示页（每个动画都有独立的路由和配置）
                        Nav.to(destination)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = description,
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
 * 动画展示页
 * 
 * @param animationName 动画名称和描述，用于显示当前使用的动画效果
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitionShowScreen(animationName: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("动画效果展示") },
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
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "这是动画效果展示页",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = animationName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "按返回键查看退出动画效果",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

