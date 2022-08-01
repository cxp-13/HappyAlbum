package com.example.happyalbum

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.happyalbum.adapter.ImageAdapter
import com.example.happyalbum.databinding.ActivityMainBinding
import com.example.happyalbum.utils.Image
import com.example.happyalbum.utils.TAG

import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        val permissions = arrayOf<String>(
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
        Log.d(TAG, "getImageFromDesc: \n")
        Log.d(TAG, "readPermission: $permission_readStorage\n")
        Log.d(TAG, "cameraPermission: $permission_camera\n")

        val image = Image(contentResolver, this)

        val imageAdapter = ImageAdapter(image)

        binding.recyclerView1.adapter = imageAdapter
        binding.recyclerView2.adapter = imageAdapter
        binding.recyclerView3.adapter = imageAdapter


    }


}