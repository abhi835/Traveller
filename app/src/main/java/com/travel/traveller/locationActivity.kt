package com.travel.traveller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.travel.traveller.Adapter.locationAdapter
import com.travel.traveller.Daos.PostDao
import com.travel.traveller.Model.location
import com.travel.traveller.databinding.ActivityLocationBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class locationActivity : AppCompatActivity(), locationAdapter.ILocationAdapter {
    lateinit var binding:ActivityLocationBinding
    private lateinit var postDao: PostDao
    private lateinit var adapter:locationAdapter
    var place = "Nalanda University"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_location)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

         place = intent.getStringExtra("place").toString()

         binding.reviewbotton.setOnClickListener {
             val intent = Intent(this, CreateReviewActivity::class.java)
             intent.putExtra("place",place)
             startActivity(intent)
         }

        setUpRecyclerView()
    }
    private fun  setUpRecyclerView(){
        postDao = PostDao()

        val postCollections = postDao.db.collection("searchPlaces").document("searchplacesList")
            .collection("commoncollection").document(place).collection("locationcollection")
        val query = postCollections.orderBy("createdAt", Query.Direction.DESCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<location>().setQuery(query,location::class.java).build()

        adapter = locationAdapter(recyclerViewOptions,this)

        binding.reviewrecyclerView.adapter = adapter
        binding.reviewrecyclerView.layoutManager = LinearLayoutManager(this)

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