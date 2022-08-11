package com.example.happyalbum.customize_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.happyalbum.utils.ImageUtils

/**
 * @Author:cxp
 * @Date: 2022/8/3 16:07
 * @Description:绘画类
 */

class HandWrite(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    var isPaint = false
    var paint: Paint? = null //定义画笔
    var origBit: Bitmap? = null //存放原始图像
    var new_1Bit: Bitmap? = null //存放从原始图像复制的位图图像
    var wid: Int = 0
    var hei: Int = 0

    //    var new_2Bit: Bitmap? = null //存放处理后的图像
    var startX = 0f
    var startY = 0f //画线的起点坐标
    var clickX = 0f
    var clickY = 0f //画线的终点坐标
    var isMove = true //设置是否画线的标记

    //    var isClear = false //设置是否清除涂鸦的标记
    var color = Color.BLUE //设置画笔的颜色
    var strokeWidth = 5.0f //设置画笔的宽度
    var imageUtils: ImageUtils = ImageUtils(this.context.contentResolver, context!!)

    // 清除涂鸦
    fun clear() {
//        isClear = true
        new_1Bit = Bitmap.createScaledBitmap(origBit!!, wid, hei, false)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(writing()!!, 0f, 0f, null)
    }

    private fun writing(): Bitmap? {  //记录绘制图形
//        var canvas: Canvas? = if (isClear) {  // 创建绘制新图形的画布
//            Canvas(new_2Bit!!)
//        } else {
//            Canvas(newBit!!) //创建绘制原图形的画布
//        }
// 定义画布
        var canvas: Canvas? = Canvas(new_1Bit!!)
        paint = Paint()
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
        paint!!.color = color
        paint!!.strokeWidth = strokeWidth
        if (isMove && isPaint) {
            canvas?.drawLine(startX, startY, clickX, clickY, paint!!) // 在画布上画线条
        }
        startX = clickX
        startY = clickY
//        return if (isClear) {
//            new_2Bit // 返回新绘制的图像
//        } else newBit
//        // 若清屏，则返回原图像

        return new_1Bit
    }

    /*invalidate()是用来刷新View的，必须是在UI线程中进行工作。比如在修改某个view的显示时，调用invalidate()才能看到重新绘制的界面。invalidate()的调用是把之前的旧的view从主UI线程队列中pop掉。 */
    // 定义触摸屏事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        clickX = event.x // 获取触摸坐标位置
        clickY = event.y
        if (event.action == MotionEvent.ACTION_DOWN) {  // 按下屏幕时无绘图
            isMove = false
            invalidate()
        } else if (event.action == MotionEvent.ACTION_MOVE) {  // 记录在屏幕上划动的轨迹
            isMove = true
            invalidate()
        }
        return true
    }

    //启动绘画
    fun start() {
        isPaint = true
    }

    //保存图片
    fun save() {
//        if (isClear)
//            imageUtils.save(new_2Bit!!)
//        else
//            imageUtils.save(new_1Bit!!)
        imageUtils.save(new_1Bit!!)
    }
}