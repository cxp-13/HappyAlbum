package com.example.happyalbum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happyalbum.entity.ImageEntity
/**
 * @Author:cxp
 * @Date: 2022/8/3 17:06
 * @Description:传递单个图片信息和路径
 * ImageAdapter被点击的图片 --> MainActivity 的点击确认的回调事件获取路径显示
*/

class ImageViewModel() : ViewModel() {
//    MutableLiveData为null时，赋值会失效
    var image: MutableLiveData<ImageEntity>? = MutableLiveData(ImageEntity("name", "location"))
//    fun setImage(imageEntity: ImageEntity){
//        image?.value = imageEntity
//    }

}