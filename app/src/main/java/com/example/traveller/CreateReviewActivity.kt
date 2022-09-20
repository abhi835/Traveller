package com.example.traveller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.traveller.Daos.PostDao
import com.example.traveller.databinding.ActivityCreateReviewBinding
import com.example.traveller.databinding.ActivityLocationBinding

class CreateReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateReviewBinding
    private lateinit var postDao: PostDao
    var place = "Nalanda University"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_review)
        binding = ActivityCreateReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        place = intent.getStringExtra("place").toString()

        postDao= PostDao()
        binding.postButton.setOnClickListener{
            val input = binding.postinput.text.toString().trim()
            if(input.isNotEmpty()){
                postDao.addPost(input,place)
                finish()         //With this it will come to main activity

            }
        }

    }
}