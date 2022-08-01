package com.example.happyalbum

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.happyalbum.databinding.ActivityEditImageBinding

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}