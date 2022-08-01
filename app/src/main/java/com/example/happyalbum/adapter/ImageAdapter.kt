package com.example.happyalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.happyalbum.R
import com.example.happyalbum.databinding.ItemBinding
import com.example.happyalbum.entity.ImageEntity
import com.example.happyalbum.utils.Image

class ImageAdapter(var image: Image) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private var imageList = image.imageList


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

        holder.dataBinding.img = imageList[position]
//        holder.dataBinding.imageView.setImageBitmap(imageList[position].bitmap)



    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}