package com.example.happyalbum.customize_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MotionEventCompat
import com.example.happyalbum.utils.ImageUtils
import kotlin.math.abs

/**
 * @Author:cxp
 * @Date: 2022/8/3 16:07
 * @Description:绘画类
 */
var TAG = "HandWrite"

class HandWrite(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    var isPaint = false
    var isScale = false
    var isRound = false


    var paint: Paint? = null //定义画笔
    var origBit: Bitmap? = null //存放原始图像
    var new_1Bit: Bitmap? = null //存放从原始图像复制的位图图像
    var wid: Float = 0f
    var hei: Float = 0f

    //    var new_2Bit: Bitmap? = null //存放处理后的图像
    var startX = 0f
    var startY = 0f //画线的起点坐标
    var clickX: Float = 0.0f
    var clickY: Float = 0.0f //画线的终点坐标
    var isMove = true //设置是否画线的标记
    private var mScaleFactor = 1f

    //    private var mScaleFactorTemp = 1f
    var mMatrix: Matrix? = Matrix()

    //    var isClear = false //设置是否清除涂鸦的标记
    var color = Color.BLUE //设置画笔的颜色
    var strokeWidth = 5.0f //设置画笔的宽度
    var imageUtils: ImageUtils = ImageUtils(this.context!!.contentResolver, context!!)
    var mGesture: ScaleGestureDetector;//用与处理双手的缩放手势


    init {
//        初始化图片拉伸类
        mGesture =
            ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
                override fun onScale(detector: ScaleGestureDetector?): Boolean {
//                    mScaleFactor = mScaleFactor * detector!!.scaleFactor
//                    继承上一次滑动的状态，不然又会图片会重新变成初始的状态  一开始scaleFactor是1
                    mScaleFactor *= detector?.scaleFactor!!
                    val currentSpan = detector?.currentSpan
                    val previousSpan = detector?.previousSpan
                    Log.d(
                        TAG,
                        "onScale: scaleFactor:$mScaleFactor  currentSpan:$currentSpan  previousSpan:$previousSpan"
                    )
//                    mMatrix?.setScale(scaleFactor!!, scaleFactor)
                    mScaleFactor = 0.5f.coerceAtLeast(mScaleFactor.coerceAtMost(5.0f))
                    Log.e(TAG, "onScale: $mScaleFactor")
                    // 以屏幕中央位置进行缩放
                    mMatrix?.setScale(
                        mScaleFactor!!,
                        mScaleFactor,
                        detector.focusX,
                        detector.focusY
                    )
                    // Don't let the object get too small or too large.
//                    mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(5.0f))
                    invalidate()
                    return false
                }

                override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                    Log.d(TAG, "onScaleBegin: ")
                    isScale = true
                    return true
                }

                override fun onScaleEnd(detector: ScaleGestureDetector?) {
                    Log.d(TAG, "onScaleEnd: ")
                    isScale = false
                }
            })
    }

    // wid-->1080, hei-->2148
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        wid = measuredWidth.toFloat()
        hei = measuredHeight.toFloat()
        clickX = wid / 2
        clickY = hei / 2
        Log.d(TAG, "onMeasure: wid-->$wid, hei-->$hei")
        new_1Bit =
            Bitmap.createScaledBitmap(origBit!!, wid.toInt(), hei.toInt(), false)
    }

    // 清除涂鸦
    fun clear() {
//        isClear = true
        new_1Bit = Bitmap.createScaledBitmap(origBit!!, wid.toInt(), hei.toInt(), false)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas?.apply {
            if (isPaint) {
                drawBitmap(writing()!!, 0f, 0f, null)
            } else {
//                单指拖动、 双指缩放
                startX = clickX
                startY = clickY
                if (isRound) {
                    val mPath = Path()
                    mPath.addCircle(
                        clickX,
                        clickY,
                        100F,
                        Path.Direction.CCW
                    )
                    canvas.clipPath(mPath)
                }
                drawBitmap(new_1Bit!!, mMatrix!!, null)
            }
        }
    }

    private fun writing(): Bitmap? {  //记录绘制图形
// 定义画布
        var canvas: Canvas? = Canvas(new_1Bit!!)
        paint = Paint()
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
        paint!!.color = color
        paint!!.strokeWidth = strokeWidth
        Log.d(TAG, "writing isScale-->: $isScale")
        if (isMove && isPaint) {
            canvas?.drawLine(startX, startY, clickX, clickY, paint!!) // 在画布上画线条
        }
        startX = clickX
        startY = clickY
        // 若清屏，则返回原图像
        return new_1Bit
    }

    /*invalidate()是用来刷新View的，必须是在UI线程中进行工作。比如在修改某个view的显示时，
    调用invalidate()才能看到重新绘制的界面。
    invalidate()的调用是把之前的旧的view从主UI线程队列中pop掉。 */
    // 定义触摸屏事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        clickX = event.x // 获取触摸坐标位置
        clickY = event.y
//        小球的边界
        if (!(clickX > 100 && clickX < wid - 100 && clickY > 100 && clickY < hei - 100)) {
            return false
        }
//        限制只能点击球的范围
        Log.d(TAG, "onTouchEvent: isRound-->$isRound,  isMove-->$isMove")
        if (isRound && !(clickX < startX + 200 && clickX > startX - 200 && clickY < startY + 200 && clickY > startY - 200)) {
            return false
        }
        if (!isPaint) {
            mGesture.onTouchEvent(event)
        }
        var action = event.action
        Log.d(TAG, "onTouchEvent: $action")
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // 按下屏幕时无绘图
                isMove = false
                invalidate()
                Toast.makeText(context, "MotionEvent.ACTION_DOWN", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onTouchEvent-->ACTION_DOWN: startX-->$startX startY-->$startY")
                Log.d(TAG, "onTouchEvent-->ACTION_DOWN: clickX-->$clickX clickY-->$clickY")
            }
            MotionEvent.ACTION_MOVE -> { // 记录在屏幕上划动的轨迹
                isMove = true
                var dx = clickX - startX
                var dy = clickY - startY
                Log.d(TAG, "onTouchEvent-->ACTION_MOVE: startX-->$startX startY-->$startY")
                Log.d(TAG, "onTouchEvent-->ACTION_MOVE: clickX-->$clickX clickY-->$clickY")
                Log.d(TAG, "onTouchEvent-->ACTION_MOVE: dx-->$dx dy-->$dy")
                if (!isPaint) {
//                    scrollBy(-dx.toInt(), -dy.toInt())
//                    单指拖动
                    mMatrix?.postTranslate(dx, dy)
                }
                invalidate()
//                if (isRound) {
//                    if (clickX > 60 && clickX < wid - 60 && clickY > 60 && clickY < hei - 60) {
//                        scrollBy(-dx.toInt(), -dy.toInt())
//                    }
//                }
            }
        }
        return true
    }

    //启动绘画
    fun start() {
        isPaint = !isPaint
        Toast.makeText(
            context,
            "${if (isPaint) "start paint" else "close paint"}",
            Toast.LENGTH_SHORT
        ).show()
        Log.d(TAG, "start: width:${wid} hei:${hei}")
        new_1Bit =
            Bitmap.createScaledBitmap(origBit!!, wid.toInt(), hei.toInt(), false)
        invalidate()
    }

    //保存图片
    fun save() {
        imageUtils.save(new_1Bit!!)
    }

    fun cropCircle() {
        isRound = !isRound
        invalidate()
    }
}