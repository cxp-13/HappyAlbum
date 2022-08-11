package com.example.happyalbum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.happyalbum.R
import com.example.happyalbum.databinding.ItemBinding
import com.example.happyalbum.entity.ImageEntity
import com.example.happyalbum.utils.ImageUtils
import com.example.happyalbum.viewmodel.ImageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author:cxp
 * @Date: 2022/8/3 16:05
 * @Description:首页图片RecyclerView适配器
 */
class ImageAdapter(
    var applicationContext: Context,
    imageUtils: ImageUtils,
    var imageViewModel: ImageViewModel,
    var imageList: ArrayList<ImageEntity>,
    var showNoticeDialog: () -> Unit,

    ) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

//    private var imageList = imageUtils.getImages()


    inner class ImageHolder(var dataBinding: ItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        var dataBinding: ItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item,
            parent,
            false
        )
        return ImageHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = imageList[position]
//        holder.dataBinding.img = image
        Glide.with(applicationContext)
            .load(image.location)
//                加圆角半径
            .transform(RoundedCorners(20))
            .into(holder.dataBinding.imageView)
//        holder.dataBinding.imageView.setImageBitmap(imageList[position].bitmap)
//        val imageView = holder.dataBinding.imageView
//给每个展示的图片绑定点击事件
        holder.dataBinding.imageView.setOnClickListener {
            imageViewModel.image?.value = image
            showNoticeDialog()
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}