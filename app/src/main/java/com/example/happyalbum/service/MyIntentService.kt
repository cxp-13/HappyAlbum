package com.example.happyalbum.service

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import com.example.happyalbum.EditImageActivity
import com.example.happyalbum.entity.ImageEntity

/**
 * @Author:cxp
 * @Date: 2022/8/5 17:23
 * @Description:本来想通过服务给编辑图片的activity传递图片信息，但貌似行不通。
 *
*/

class MyIntentService : IntentService("MyIntentService") {

    var myBinder: MyBinder? = null

    inner class MyBinder : Binder() {
        var imageEntity: ImageEntity? = null

        fun getImage(): ImageEntity? {
            return imageEntity
        }
    }

    override fun onCreate() {
        super.onCreate()
        myBinder = MyBinder()
    }

    override fun onHandleIntent(intent: Intent?) {
        val bundle: Bundle? = intent?.getBundleExtra("bd")
        myBinder?.imageEntity = bundle?.getSerializable("imageEntity") as ImageEntity
    }

    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }
}