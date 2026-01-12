package com.kajlee.tnav_android.router.des

import androidx.navigation.NavGraphBuilder
import com.kajlee.tnav.Destination
import com.kajlee.tnav.NavTransitions
import com.kajlee.tnav.composableWithDestination
import com.kajlee.tnav.dialogWithDestination
import com.kajlee.tnav_android.ui.pages.demo.BackToDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.BackToPage1Screen
import com.kajlee.tnav_android.ui.pages.demo.BackToPage2Screen
import com.kajlee.tnav_android.ui.pages.demo.ComplexDataDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.ComplexDataDetailScreen
import com.kajlee.tnav_android.ui.pages.demo.DemoListScreen
import com.kajlee.tnav_android.ui.pages.demo.DialogContentScreen
import com.kajlee.tnav_android.ui.pages.demo.DialogDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.OffAllDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.OffAllTargetScreen
import com.kajlee.tnav_android.ui.pages.demo.ParamsDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.ReplaceDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.ReplaceTargetScreen
import com.kajlee.tnav_android.ui.pages.demo.ResultDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.SealedClassDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.SealedClassResultScreen
import com.kajlee.tnav_android.ui.pages.demo.SelectionListScreen
import com.kajlee.tnav_android.ui.pages.demo.SimpleDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.SingleTopDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.TransitionShowScreen
import com.kajlee.tnav_android.ui.pages.demo.TransitionsDemoScreen
import com.kajlee.tnav_android.ui.pages.demo.UserDetailScreen

/**
 * 演示页面路由定义
 * 
 * 这里定义所有 TNav 功能演示页面的路由。
 * 每个路由对应一个演示页面，展示 TNav 的不同功能特性。
 */
object DemoDes {
    /** 演示列表页（所有演示功能的入口） */
    object DemoList : Destination("DemoList")
    
    // ========== 基础导航演示 ==========
    /** 简单导航演示（不带参数的页面跳转） */
    object SimpleDemo : Destination("SimpleDemo")
    
    /** 带参数导航演示 */
    object ParamsDemo : Destination("ParamsDemo")
    
    /** 替换页面演示 */
    object ReplaceDemo : Destination("ReplaceDemo")
    
    /** 替换目标页 */
    object ReplaceTarget : Destination("ReplaceTarget")
    
    /** 清空栈演示 */
    object OffAllDemo : Destination("OffAllDemo")
    
    /** 清空栈目标页 */
    object OffAllTarget : Destination("OffAllTarget")
    
    /** 返回到指定页面演示 */
    object BackToDemo : Destination("BackToDemo")
    
    /** 返回到指定页面 - 中间页1 */
    object BackToPage1 : Destination("BackToPage1")
    
    /** 返回到指定页面 - 中间页2 */
    object BackToPage2 : Destination("BackToPage2")
    
    /** 单例模式演示 */
    object SingleTopDemo : Destination("SingleTopDemo")
    
    // ========== 参数传递演示 ==========
    /** 用户详情页（基本对象传递） */
    object UserDetail : Destination("UserDetail")
    
    /** 复杂对象传递演示 */
    object ComplexDataDemo : Destination("ComplexDataDemo")
    
    /** 复杂对象详情页 */
    object ComplexDataDetail : Destination("ComplexDataDetail")
    
    /** Sealed Class 传递演示 */
    object SealedClassDemo : Destination("SealedClassDemo")
    
    /** Sealed Class 结果页 */
    object SealedClassResult : Destination("SealedClassResult")
    
    // ========== 返回结果演示 ==========
    /** 返回结果演示 */
    object ResultDemo : Destination("ResultDemo")
    
    /** 选择列表页 */
    object SelectionList : Destination("SelectionList")
    
    // ========== 动画效果演示 ==========
    /** 动画效果演示列表 */
    object TransitionsDemo : Destination("TransitionsDemo")
    
    /** 默认动画展示页 */
    object TransitionDefault : Destination("TransitionDefault")
    
    /** 淡入淡出动画展示页 */
    object TransitionFade : Destination("TransitionFade")
    
    /** 缩放动画展示页 */
    object TransitionScale : Destination("TransitionScale")
    
    /** 垂直滑动动画展示页 */
    object TransitionSlideVertical : Destination("TransitionSlideVertical")
    
    /** 弹性缩放动画展示页 */
    object TransitionElastic : Destination("TransitionElastic")
    
    /** 滑动+淡入淡出动画展示页 */
    object TransitionSlideFade : Destination("TransitionSlideFade")
    
    /** 缩放+滑动动画展示页 */
    object TransitionScaleSlide : Destination("TransitionScaleSlide")
    
    /** 底部弹出动画展示页 */
    object TransitionBottomSheet : Destination("TransitionBottomSheet")
    
    /** 旋转+缩放动画展示页 */
    object TransitionRotateScale : Destination("TransitionRotateScale")
    
    /** 快速淡入淡出动画展示页 */
    object TransitionQuickFade : Destination("TransitionQuickFade")
    
    /** 无动画展示页 */
    object TransitionNone : Destination("TransitionNone")
    
    // ========== 对话框演示 ==========
    /** 对话框演示 */
    object DialogDemo : Destination("DialogDemo")
    
    /** 对话框内容页 */
    object DialogContent : Destination("DialogContent")
}

/**
 * 注册演示页面路由
 * 
 * 使用 TNav 的 composableWithDestination 函数注册页面路由。
 * 可以为不同页面设置不同的动画效果（通过 transitions 参数）。
 */
fun NavGraphBuilder.registerDemoRoute() {
    // 注册演示列表页（默认动画）
    composableWithDestination(DemoDes.DemoList) {
        DemoListScreen()
    }
    
    // ========== 基础导航演示 ==========
    composableWithDestination(DemoDes.SimpleDemo) {
        SimpleDemoScreen()
    }
    
    composableWithDestination(DemoDes.ParamsDemo) {
        ParamsDemoScreen()
    }
    
    composableWithDestination(DemoDes.UserDetail) {
        UserDetailScreen()
    }
    
    composableWithDestination(DemoDes.ReplaceDemo) {
        ReplaceDemoScreen()
    }
    
    composableWithDestination(DemoDes.ReplaceTarget) {
        ReplaceTargetScreen()
    }
    
    composableWithDestination(DemoDes.OffAllDemo) {
        OffAllDemoScreen()
    }
    
    composableWithDestination(DemoDes.OffAllTarget) {
        OffAllTargetScreen()
    }
    
    composableWithDestination(DemoDes.BackToDemo) {
        BackToDemoScreen()
    }
    
    composableWithDestination(DemoDes.BackToPage1) {
        BackToPage1Screen()
    }
    
    composableWithDestination(DemoDes.BackToPage2) {
        BackToPage2Screen()
    }
    
    composableWithDestination(DemoDes.SingleTopDemo) {
        SingleTopDemoScreen()
    }
    
    // ========== 参数传递演示 ==========
    composableWithDestination(DemoDes.ComplexDataDemo) {
        ComplexDataDemoScreen()
    }
    
    composableWithDestination(DemoDes.ComplexDataDetail) {
        ComplexDataDetailScreen()
    }
    
    composableWithDestination(DemoDes.SealedClassDemo) {
        SealedClassDemoScreen()
    }
    
    composableWithDestination(DemoDes.SealedClassResult) {
        SealedClassResultScreen()
    }
    
    // ========== 返回结果演示 ==========
    composableWithDestination(DemoDes.ResultDemo) {
        ResultDemoScreen()
    }
    
    composableWithDestination(DemoDes.SelectionList) {
        SelectionListScreen()
    }
    
    // ========== 动画效果演示 ==========
    composableWithDestination(DemoDes.TransitionsDemo) {
        TransitionsDemoScreen()
    }
    
    // 为每个动画效果配置对应的路由和动画
    composableWithDestination(
        destination = DemoDes.TransitionDefault,
        transitions = NavTransitions.Default
    ) {
        TransitionShowScreen("Default - 水平滑动（默认）")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionFade,
        transitions = NavTransitions.Fade
    ) {
        TransitionShowScreen("Fade - 淡入淡出")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionScale,
        transitions = NavTransitions.Scale
    ) {
        TransitionShowScreen("Scale - 缩放+淡入淡出")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionSlideVertical,
        transitions = NavTransitions.SlideVertical
    ) {
        TransitionShowScreen("SlideVertical - 垂直滑动")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionElastic,
        transitions = NavTransitions.Elastic
    ) {
        TransitionShowScreen("Elastic - 弹性缩放（炫酷）")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionSlideFade,
        transitions = NavTransitions.SlideFade
    ) {
        TransitionShowScreen("SlideFade - 滑动+淡入淡出")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionScaleSlide,
        transitions = NavTransitions.ScaleSlide
    ) {
        TransitionShowScreen("ScaleSlide - 缩放+滑动组合")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionBottomSheet,
        transitions = NavTransitions.BottomSheet
    ) {
        TransitionShowScreen("BottomSheet - 从底部弹出")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionRotateScale,
        transitions = NavTransitions.RotateScale
    ) {
        TransitionShowScreen("RotateScale - 旋转+缩放（炫酷）")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionQuickFade,
        transitions = NavTransitions.QuickFade
    ) {
        TransitionShowScreen("QuickFade - 快速淡入淡出")
    }
    
    composableWithDestination(
        destination = DemoDes.TransitionNone,
        transitions = NavTransitions.None
    ) {
        TransitionShowScreen("None - 无动画")
    }
    
    // ========== 对话框演示 ==========
    composableWithDestination(DemoDes.DialogDemo) {
        DialogDemoScreen()
    }
    
    dialogWithDestination(DemoDes.DialogContent) {
        DialogContentScreen()
    }
}

