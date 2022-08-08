package com.example.happyalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.happyalbum.R
import com.example.happyalbum.databinding.ItemBinding
import com.example.happyalbum.utils.ImageUtils
import com.example.happyalbum.viewmodel.ImageViewModel

/**
 * @Author:cxp
 * @Date: 2022/8/3 16:05
 * @Description:首页图片RecyclerView适配器
*/
class ImageAdapter(
    imageUtils: ImageUtils,
    var imageViewModel: ImageViewModel,
    var showNoticeDialog: () -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private var imageList = imageUtils.getImages()

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
        holder.dataBinding.img = image
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