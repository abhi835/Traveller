package com.example.traveller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.traveller.databinding.ActivityFullPhotoBinding


class UserPhotosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_full_photo)
        binding = ActivityFullPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val  city = intent.getStringExtra("photoarray")
        val ss = intent.getStringExtra("photoarray")
//        Toast.makeText(this,city+"   is goood",Toast.LENGTH_SHORT).show()
//binding.placephotosInFullPhoto.setOnClickListener{
//            Toast.makeText(this,ss,Toast.LENGTH_SHORT).show()
//}


    }
}