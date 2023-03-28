package com.travel.traveller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.travel.traveller.Daos.PostDao
import com.travel.traveller.databinding.ActivityCreateReviewBinding

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