package com.travel.traveller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.travel.traveller.Adapter.IPostAdapter
import com.travel.traveller.Adapter.NewStatesAdapter
import com.travel.traveller.Model.NewStates
import com.travel.traveller.databinding.ActivityMainBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.ads.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity(),IPostAdapter {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:NewStatesAdapter
    val db = FirebaseFirestore.getInstance()
    val stateCollection = db.collection("States")
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ///Ad
         MobileAds.initialize(this) {
         }
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }
            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                mAdView.loadAd(adRequest)
            }
            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded()
            }
            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }








        ////////////Ad ends
        supportActionBar?.hide()
       binding.searchplacebutton.setOnClickListener{
           val intent = Intent(this,searchplaceActivity::class.java)
           startActivity(intent)
       }

        setUpRecyclerView()



    }

    private fun setUpRecyclerView() {


        val postCollections = stateCollection
        val query = postCollections.orderBy("stateRating", Query.Direction.ASCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<NewStates>().setQuery(query,NewStates::class.java).build()

        adapter = NewStatesAdapter(recyclerViewOptions,this)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.itemAnimator = null
    }
    override fun onStart() {    //We want adapter to listen the changes made in database so we have created onstart function which will start listening once the app start
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {    //and we Adapter to stop listen if app closes
        super.onStop()
        adapter.stopListening()
    }

    private  fun getStateById(postId:String): Task<DocumentSnapshot> {
        return stateCollection.document(postId).get()
    }
    override fun onStateClicked(postId: String) {
//        val stateid = getStateById(postId)
//            Toast.makeText(this,"$postId Clicked",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,StateCitiesActivity::class.java)
        intent.putExtra("stateId",postId)
        startActivity(intent)

//        stateCollection.document(postId).collection("Nalanda")

    }
}