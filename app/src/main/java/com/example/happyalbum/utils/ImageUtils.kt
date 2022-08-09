package com.example.happyalbum.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.happyalbum.entity.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.OutputStream

/**
 * @Author:cxp
 * @Date: 2022/8/3 16:57
 * @Description:图片工具类
 */

const val TAG = "ImgActivity"

@SuppressLint("Range")
class ImageUtils(var contentResolver: ContentResolver, var context: Context) {
    //    获取游标，时间降序
    private var cursor: Cursor?? = null

    // TODO: 修改到子线程
    init {

    }

    //存放图片列表
    suspend fun getImages(): ArrayList<ImageEntity> {
        var imageList = ArrayList<ImageEntity>()

        cursor = contentResolver.query(
//       要先获取权限，不然MediaStore.Images.Media.EXTERNAL_CONTENT_URI是null
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media.MIME_TYPE + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?",
            arrayOf("image/jpeg", "image/png"),
            MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        )

        while (cursor!!.moveToNext()) {
            //获取图片的名称
            val name: String =
                cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
            Log.d(TAG, "initImages: imageName: $name")
            //获取图片的路径
            val data: ByteArray =
                cursor!!.getBlob(cursor!!.getColumnIndex(MediaStore.Images.Media.DATA))
            val location = String(data, 0, data.size - 1)
            Log.d(TAG, "initImages: imageLocation: $location")
            val date =
                cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED))
            Log.d(TAG, "initImages: imageDate: $date")
            //根据路径获取图片 bm属性暂时无效
//                val bm: Bitmap? = getImgFromDesc(location)
            //获取图片的详细信息
//                val desc: String =
//                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION))
//                Log.d(TAG, "initImages: ImageDesc: $desc")
            val image = ImageEntity(name, location)
            imageList.add(image)
        }
        Log.d(TAG, "initImage: " + "imageList.size: " + imageList.size)
        return imageList
    }

    //根据路径获取图片(弃用原因：通过imageView的src属性传入location图片路径)
//    弃用原因2.0：通过ImageEntity的文件路径location就可以创建位图， 自定义view
    @Deprecated(message = "通过ImageEntity的文件路径location就可以创建位图， 自定义view")
    fun getImgFromDesc(path: String): Bitmap? {
        var bm: Bitmap? = null
        val file = File(path)
        if (file.exists()) {
            bm = BitmapFactory.decodeFile(path)
        } else {

            Log.d(TAG, "getImgFromDesc: 该图片不存在！")
        }
        return bm
    }

    //保存图片
    fun save(bitmap: Bitmap) {
        //创建ContentValues对象，准备插入数据
        var contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "test")
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        //插入数据，返回所插入数据对应的Uri
        var uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        //加载应用程序res下的图片bitmap

        //获取刚插入的数据的Uri对应的输出流
        var outputStream: OutputStream = contentResolver.openOutputStream(uri!!)!!
        //将bitmap图片保存到Uri对应的数据节点中
        var isSaveSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        //图片会保存到sd卡的pcitures目录下1487231905572.jpg
        if (isSaveSuccess) {
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
        }
        outputStream.close()
    }
}