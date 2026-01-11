package com.kajinl.tnav

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.navigation.NavBackStackEntry

/**
 * 导航动画预设
 * 提供多种预设的进入和退出动画效果
 */
object NavTransitions {
    
    /**
     * 默认动画：水平滑动
     */
    val Default = TransitionConfig(
        enter = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) },
        exit = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) },
        popEnter = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) },
        popExit = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) }
    )
    
    /**
     * 淡入淡出动画
     */
    val Fade = TransitionConfig(
        enter = { fadeIn(animationSpec = tween(300)) },
        exit = { fadeOut(animationSpec = tween(300)) },
        popEnter = { fadeIn(animationSpec = tween(300)) },
        popExit = { fadeOut(animationSpec = tween(300)) }
    )
    
    /**
     * 缩放动画
     */
    val Scale = TransitionConfig(
        enter = { 
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(300))
        },
        exit = { 
            scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnter = { 
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExit = { 
            scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(300))
        }
    )
    
    /**
     * 垂直滑动动画
     */
    val SlideVertical = TransitionConfig(
        enter = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)) },
        exit = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300)) },
        popEnter = { slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)) },
        popExit = { slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)) }
    )
    
    /**
     * 弹性缩放动画（炫酷效果）
     */
    val Elastic = TransitionConfig(
        enter = {
            scaleIn(
                initialScale = 0.5f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(animationSpec = tween(400))
        },
        exit = {
            scaleOut(
                targetScale = 0.5f,
                animationSpec = tween(250, easing = FastOutLinearInEasing)
            ) + fadeOut(animationSpec = tween(250))
        },
        popEnter = {
            scaleIn(
                initialScale = 0.5f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(animationSpec = tween(400))
        },
        popExit = {
            scaleOut(
                targetScale = 0.5f,
                animationSpec = tween(250, easing = FastOutLinearInEasing)
            ) + fadeOut(animationSpec = tween(250))
        }
    )
    
    /**
     * 滑动+淡入淡出组合动画
     */
    val SlideFade = TransitionConfig(
        enter = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(350, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(350))
        },
        exit = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(350, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(350))
        },
        popEnter = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(350, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(350))
        },
        popExit = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(350, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(350))
        }
    )
    
    /**
     * 缩放+滑动组合动画
     */
    val ScaleSlide = TransitionConfig(
        enter = {
            scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideInHorizontally(
                initialOffsetX = { (it * 0.3).toInt() },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(300))
        },
        exit = {
            scaleOut(
                targetScale = 0.9f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideOutHorizontally(
                targetOffsetX = { (-it * 0.3).toInt() },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnter = {
            scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideInHorizontally(
                initialOffsetX = { (-it * 0.3).toInt() },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExit = {
            scaleOut(
                targetScale = 0.9f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + slideOutHorizontally(
                targetOffsetX = { (it * 0.3).toInt() },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(300))
        }
    )
    
    /**
     * 从底部弹出动画（类似对话框）
     */
    val BottomSheet = TransitionConfig(
        enter = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400))
        },
        exit = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnter = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExit = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            ) + fadeOut(animationSpec = tween(300))
        }
    )
    
    /**
     * 旋转+缩放动画（炫酷效果）
     */
    val RotateScale = TransitionConfig(
        enter = {
            scaleIn(
                initialScale = 0.7f,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400)) + 
            slideInHorizontally(
                initialOffsetX = { (it * 0.5).toInt() },
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )
        },
        exit = {
            scaleOut(
                targetScale = 0.7f,
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            ) + fadeOut(animationSpec = tween(300)) +
            slideOutHorizontally(
                targetOffsetX = { (-it * 0.5).toInt() },
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            )
        },
        popEnter = {
            scaleIn(
                initialScale = 0.7f,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400)) +
            slideInHorizontally(
                initialOffsetX = { (-it * 0.5).toInt() },
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )
        },
        popExit = {
            scaleOut(
                targetScale = 0.7f,
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            ) + fadeOut(animationSpec = tween(300)) +
            slideOutHorizontally(
                targetOffsetX = { (it * 0.5).toInt() },
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            )
        }
    )
    
    /**
     * 快速淡入淡出动画
     */
    val QuickFade = TransitionConfig(
        enter = { fadeIn(animationSpec = tween(200)) },
        exit = { fadeOut(animationSpec = tween(150)) },
        popEnter = { fadeIn(animationSpec = tween(200)) },
        popExit = { fadeOut(animationSpec = tween(150)) }
    )
    
    /**
     * 无动画
     */
    val None = TransitionConfig(
        enter = { null },
        exit = { null },
        popEnter = { null },
        popExit = { null }
    )
}

/**
 * 动画配置数据类
 * 封装进入、退出、返回进入、返回退出四种动画
 */
data class TransitionConfig(
    val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?,
    val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?,
    val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = enter,
    val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = exit
)

