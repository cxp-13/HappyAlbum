package com.example.happyalbum

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.WindowMetrics
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.happyalbum.customize_view.HandWrite
import com.example.happyalbum.databinding.ActivityEditImageBinding
import com.example.happyalbum.entity.ImageEntity
import kotlinx.coroutines.*

/**
 * @Author:cxp
 * @Date: 2022/8/3 16:40
 * @Description:编辑图片页
 */

//RecyclerView
//LayoutManager=>LinearLayoutManager、GridLayoutManager、StaggeredLayoutManager
const val TAG = "EditImageActivity"

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding
    private lateinit var handWrite: HandWrite
    private lateinit var handle: Handler
    var imageEntity: ImageEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//获取从图片展示页传过来的intent
        val intent = intent
        val bundle: Bundle? = intent.getBundleExtra("bd")
        imageEntity = bundle?.getSerializable("imageEntity") as ImageEntity?

//初始化绘画类
        handWrite = binding.hw
        // 从资源中获取原始图像
        handWrite.origBit =
            BitmapFactory.decodeFile(imageEntity?.location).copy(Bitmap.Config.ARGB_8888, true)
        //当一个view对象创建时，android并不知道其大小，所以getWidth()和getHeight()返回的结果是0
        // 建立原始图像的位图 width:1440 height:2112 先debug获取View的长宽

        val width = windowManager.defaultDisplay.width

        val height = windowManager.defaultDisplay.height

        handWrite.new_1Bit =
            Bitmap.createScaledBitmap(handWrite.origBit!!, width, height, false)


//        handWrite.new_1Bit = Bitmap.createBitmap(handWrite.origBit!!)


//定义handle处理图片的名称
        /*对象表达式常用来作为匿名内部类的实现，与对象声明不同，匿名对象不是单例的，每次对象表达式被执行都会创建一个新的对象实例*/
        handle = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                //        显示待涂鸦图片的名称
                binding.showLocation.text = msg.obj.toString()
            }
        }

        handle.postDelayed({
//            将intent中的图像的名称传给handle
            var message: Message = handle.obtainMessage()
            message.obj = imageEntity?.name
            handle.handleMessage(message)
        }, 2000)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
//        保存图片
        var saveBt = binding.saveBt
        saveBt.setOnClickListener {
            handWrite.save()
        }
//        编辑图片
        var editBt = binding.editBt
        editBt.setOnClickListener {
            handWrite.start()
        }
//        清楚涂鸦
        var clearBt = binding.clearBt
        clearBt.setOnClickListener {
            handWrite.clear()
        }
//        返回展示图片页
        var backBt = binding.backBt
        backBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
//        var paint = Paint()
//        paint.strokeWidth = 10F
//        paint.color = Color.RED
//        val imageView = binding.imageView
////当一个view对象创建时，android并不知道其大小，所以getWidth()和getHeight()返回的结果是0
//        var startX = 0f
//        var startY = 0f
//        var canvas: Canvas?
//先debug获取imageView的长宽
//        baseBitmap = Bitmap.createBitmap(
//            1440,
//            2112,
//            Bitmap.Config.ARGB_8888
//        )
//        var baseBitmap =
//            BitmapFactory.decodeFile(imageEntity?.location).copy(Bitmap.Config.ARGB_8888, true)
//        var newBitmap = Bitmap.createScaledBitmap(baseBitmap, 1440, 2112, false)
//
//        canvas = Canvas(newBitmap)
//        binding.imageView.setImageBitmap(newBitmap)
//
//        imageView.setOnTouchListener { v, event ->
//            when (event.action) {
//                // 用户按下动作
//                MotionEvent.ACTION_DOWN -> {
//                    // 记录开始触摸的点的坐标
//                    startX = event.x
//                    startY = event.y
//                }
//                // 用户手指在屏幕上移动的动作
//                MotionEvent.ACTION_MOVE -> {
//                    // 记录移动位置的点的坐标
//                    var stopX = event.x
//                    var stopY = event.y
//                    //根据两点坐标，绘制连线
//                    if (isPaint) {
//                        canvas?.drawLine(startX, startY, stopX, stopY, paint)
//                    }
//                    // 更新开始点的位置
//                    startX = event.x
//                    startY = event.y
//                    // 把图片展示到ImageView中
//                    binding.imageView.setImageBitmap(newBitmap)
//                }
//                MotionEvent.ACTION_UP -> {}
//                else -> {}
//            }
//            true
//        }
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




