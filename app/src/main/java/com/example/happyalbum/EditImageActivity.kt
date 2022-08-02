package com.example.happyalbum

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.happyalbum.canvas.MyView
import com.example.happyalbum.databinding.ActivityEditImageBinding
import com.example.happyalbum.entity.ImageEntity

const val TAG = "EditImageActivity"

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")

        val intent = intent
        val bundle: Bundle? = intent.getBundleExtra("bd")
        val imageEntity = bundle?.getSerializable("imageEntity") as ImageEntity?
        binding.img = imageEntity

        var paint = Paint()
        paint.strokeWidth = 10F
        paint.color = Color.RED
        val imageView = binding.imageView

//当一个view对象创建时，android并不知道其大小，所以getWidth()和getHeight()返回的结果是0


        var startX = 0f
        var startY = 0f
        var baseBitmap:Bitmap ?= null

        var canvas: Canvas ?= null

        imageView.setOnTouchListener { v, event ->

            when (event.action) {
                // 用户按下动作
                MotionEvent.ACTION_DOWN -> {
                    // 第一次绘图初始化内存图片，指定背景为白色
                    if(baseBitmap == null){
                        baseBitmap = Bitmap.createBitmap(
                            imageView.width,
                            imageView.height,
                            Bitmap.Config.ARGB_8888
                        )
                        canvas = Canvas(baseBitmap)

                    }
                    // 记录开始触摸的点的坐标

                    startX = event.x
                    startY = event.y
                }
                // 用户手指在屏幕上移动的动作
                MotionEvent.ACTION_MOVE -> {

                    // 记录移动位置的点的坐标
                    var stopX = event.x
                    var stopY = event.y
                    //根据两点坐标，绘制连线
                    canvas?.drawLine(startX, startY, stopX, stopY, paint);

                    // 更新开始点的位置
                    startX = event.x
                    startY = event.y

                    // 把图片展示到ImageView中
                    binding.imageView.setImageBitmap(baseBitmap)

                }
                MotionEvent.ACTION_UP -> {}
                else -> {}
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }


}




