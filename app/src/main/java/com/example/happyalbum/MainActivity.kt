package com.example.happyalbum

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.happyalbum.adapter.ImageAdapter
import com.example.happyalbum.databinding.ActivityMainBinding
import com.example.happyalbum.fragment.NoticeDialogFragment
import com.example.happyalbum.utils.ImageUtils
import com.example.happyalbum.viewmodel.ImageViewModel
/**
 * @Author:cxp
 * @Date: 2022/8/3 17:14
 * @Description:图片展示页
*/

const val TAG2 = "MainActivity"

class MainActivity : FragmentActivity(), NoticeDialogFragment.NoticeDialogListener {

    private lateinit var binding: ActivityMainBinding
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG2, "onCreate: ")
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
//检查权限
        permissionChecking()
//初始化图片工具类
        val imageUtils = ImageUtils(contentResolver, applicationContext)
//初始化图片适配器
        val imageAdapter = ImageAdapter(imageUtils, imageViewModel) { showNoticeDialog() }
//给三个recyclerView的适配器赋值
        binding.recyclerView1.adapter = imageAdapter
        binding.recyclerView2.adapter = imageAdapter
        binding.recyclerView3.adapter = imageAdapter
    }
//检查权限
    private fun permissionChecking() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        val REQUEST_CODE = 10001;
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            for (permission in permissions) {
                //  GRANTED---授权  DINIED---拒绝
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
                }
            }
        }
        val permission_readStorage = ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val permission_camera = ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        Log.d(TAG2, "getImageFromDesc: \n")
        Log.d(TAG2, "readPermission: $permission_readStorage\n")
        Log.d(TAG2, "cameraPermission: $permission_camera\n")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG2, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG2, "onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG2, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG2, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG2, "onDestroy: ")
    }

    //创建对话框片段的实例并显示它
    private fun showNoticeDialog() {
        var msg = "test"
//        获取图片的路径并显示在对话框的标题上
        imageViewModel.image?.observe(this) {
            msg = it.location
        }
        val dialog = NoticeDialogFragment(msg)
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    //当点击确认
    override fun onDialogPositiveClick() {
/*
bug：直接无视弹出框跳转到编辑页
解决：把观察者的范围改成bundle的赋值
* */
        val intent = Intent(this, EditImageActivity::class.java)
        val bundle = Bundle()
        imageViewModel.image?.observe(this) {
            bundle.putSerializable("imageEntity", it)
        }
        intent.putExtra("bd", bundle)
        startActivity(intent)
    }

    //当点击取消
    override fun onDialogNegativeClick() {

    }
}