package com.example.happyalbum.canvas


import android.content.Context
import android.graphics.*
import android.view.View


class MyView(context: Context?,var bitmap: Bitmap) : View(context) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var paint = Paint()
        var path = Path()
        val canvasWidth = canvas!!.width
        val deltaX = canvasWidth / 4
        val deltaY = (deltaX * 0.75).toInt()

        paint.color = 0xcc0033 //设置画笔颜色

        paint.strokeWidth = 10F //设置线宽

        paint.style = Paint.Style.STROKE //设置画笔为线条模式
        val arcRecF = RectF(0F, 0F, deltaX.toFloat(), deltaY.toFloat())
        path.addArc(arcRecF, 0f, 135f)
//        canvas.translate(0F, (deltaY * 2).toFloat())

        canvas.drawPath(path, paint)


    }
}