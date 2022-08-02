package com.example.happyalbum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happyalbum.entity.ImageEntity

class ImageViewModel() : ViewModel() {
//    MutableLiveData为null时，赋值会失效
    var image: MutableLiveData<ImageEntity>? = MutableLiveData(ImageEntity("name", "location"))
//    fun setImage(imageEntity: ImageEntity){
//        image?.value = imageEntity
//    }

}