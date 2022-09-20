package com.example.traveller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveller.Adapter.locationAdapter
import com.example.traveller.Adapter.reviewAdapter
import com.example.traveller.Daos.PostDao
import com.example.traveller.Model.location
import com.example.traveller.Model.review
import com.example.traveller.databinding.ActivityReviewBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class reviewActivity : AppCompatActivity(), reviewAdapter.IReviewAdapter {
    lateinit var binding: ActivityReviewBinding
    private lateinit var postDao: PostDao
    private lateinit var adapter: reviewAdapter
    var place = "Nalanda University"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_review)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        place = intent.getStringExtra("place").toString()


        binding.reviewPostBtn.setOnClickListener{
             val reviewtext = binding.reviewET.getText().toString().trim()
            if(reviewtext.isNotEmpty()){
                postDao.addreview(reviewtext,place)
//                finish()         //With this it will come to main activity

            }

        }
        setUpRecyclerView()
    }
    private fun  setUpRecyclerView(){
        postDao = PostDao()

        val postCollections = postDao.db.collection("searchPlaces").document("searchplacesList")
            .collection("commoncollection").document(place).collection("reviewcollection")
        val query = postCollections.orderBy("createdAt", Query.Direction.DESCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<review>().setQuery(query,
            review::class.java).build()

        adapter = reviewAdapter(recyclerViewOptions,this)

        binding.reviewRV.adapter = adapter
        binding.reviewRV.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {    //We want adapter to listen the changes made in database so we have created onstart function which will start listening once the app start
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {    //and we Adapter to stop listen if app closes
        super.onStop()
        adapter.stopListening()
    }

    override fun onVoteClicked(postId: String) {
        postDao.upgradevotes(postId,place)

    }

    override fun onVotedownClicked(postId: String) {
        postDao.downgradevotes(postId,place)
    }
}