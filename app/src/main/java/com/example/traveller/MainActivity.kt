package com.example.traveller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.traveller.Adapter.IPostAdapter
import com.example.traveller.Adapter.NewStatesAdapter
import com.example.traveller.Model.NewStates
import com.example.traveller.databinding.ActivityMainBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity(),IPostAdapter {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:NewStatesAdapter
    val db = FirebaseFirestore.getInstance()
    val stateCollection = db.collection("States")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpRecyclerView()



    }

    private fun setUpRecyclerView() {


        val postCollections = stateCollection
        val query = postCollections.orderBy("stateRating", Query.Direction.ASCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<NewStates>().setQuery(query,NewStates::class.java).build()

        adapter = NewStatesAdapter(recyclerViewOptions,this)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(this,2)
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
            Toast.makeText(this,"$postId Clicked",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,StateCitiesActivity::class.java)
        intent.putExtra("stateId",postId)
        startActivity(intent)

//        stateCollection.document(postId).collection("Nalanda")

    }
}