package com.example.happyalbum.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.happyalbum.MainActivity
import com.example.happyalbum.entity.ImageEntity
import java.io.File

@SuppressLint("Range")
const val TAG = "ImgActivity"

@SuppressLint("Range")
class Image(contentResolver: ContentResolver, var activity: MainActivity) {
    private val cursor: Cursor? = contentResolver.query(
//       要先获取权限，不然MediaStore.Images.Media.EXTERNAL_CONTENT_URI是null
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=?", arrayOf("image/jpeg", "image/png"), MediaStore.Images.Media.DATE_MODIFIED
    )

    var imageList = ArrayList<ImageEntity>()

    init {
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //获取图片的名称
                val name: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                Log.d(TAG, "initImages: imageName: $name")

                //获取图片的路径
                val data: ByteArray =
                    cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                val location = String(data, 0, data.size - 1)
                Log.d(TAG, "initImages: imageLocation: $location")
                //根据路径获取图片 bm属性暂时无效
                val bm: Bitmap? = getImgFromDesc(location)
                //获取图片的详细信息
//                val desc: String =
//                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION))
//                Log.d(TAG, "initImages: ImageDesc: $desc")
                val image = ImageEntity(bm, name, location)
                imageList.add(image)
            }
        }
        Log.d(TAG, "initImage: " + "imageList.size: " + imageList.size)
    }
    //根据路径获取图片
    private fun getImgFromDesc(path: String): Bitmap? {
        var bm: Bitmap? = null
        val file = File(path)
        if (file.exists()) {
            bm = BitmapFactory.decodeFile(path)
        } else {

            Log.d(TAG, "getImgFromDesc: 该图片不存在！")
        }
        return bm
    }
}