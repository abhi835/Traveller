package com.example.traveller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveller.Adapter.IPostAdapter
import com.example.traveller.Adapter.NewStatesAdapter
import com.example.traveller.Model.NewStates
import com.example.traveller.databinding.ActivityMainBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
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

//        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//        recyclerview.layoutManager = GridLayoutManager(this,2)
//        val data = ArrayList<States>()
//
//
//
//        data.add(0,States("Goa", R.drawable.goa))
//        data.add(1,States("Bihar",R.drawable.bihar))
//        data.add(2,States("Andaman and Nicobar Islands",R.drawable.andbarnikobar))
//        data.add(3,States("Jammu and Kashmir",R.drawable.jammukashmir))
//        data.add(4,States("Arunachal Pradesh",R.drawable.arunachalpradesh))
//        data.add(5,States("Assam",R.drawable.assam))
//        data.add(6,States("Delhi",R.drawable.delhi))
//        data.add(7,States("Maharashtra",R.drawable.maharastra))
//        data.add(8,States("Gujarat",R.drawable.gujrat))
//        data.add(9,States("Himachal Pradesh",R.drawable.himachalpradesh))
//        data.add(10,States("Karnataka",R.drawable.karnataka))
//        data.add(11,States("Kerala",R.drawable.kerela))
//        data.add(12,States("Lakshadweep (Union Territory)",R.drawable.lakshadweep))
//        data.add(13,States("Maharashtra",R.drawable.maharastra))
//        data.add(14,States("Manipur",R.drawable.manipur))
//        data.add(15,States("Meghalaya",R.drawable.meghalaya))
//        data.add(16,States("Mizoram",R.drawable.mizoram))
//        data.add(17,States("Nagaland",R.drawable.nagaland))
//        data.add(18,States("Odisha",R.drawable.odisha))
//        data.add(19,States("Punjab",R.drawable.punjab))
//        data.add(20,States("Rajasthan",R.drawable.rajasthan))
//        data.add(21,States("Sikkim",R.drawable.sikkim))
//        data.add(22,States("Tamil Nadu",R.drawable.tamilnadu))
//        data.add(23,States("Tripura",R.drawable.tripura))
//        data.add(24,States("Uttar Pradesh",R.drawable.up))
//        data.add(25,States("Uttarakhand",R.drawable.uttarakhand))
//        data.add(26,States("West Bengal",R.drawable.westbengal))
//        data.add(27,States("Jharkhand",R.drawable.jharkhand))
//        data.add(28,States("Himachal Pradesh",R.drawable.himachalpradesh))
//        data.add(29,States("Haryana",R.drawable.haryana))
//        data.add(30,States("Chhattisgarh",R.drawable.chattisgarh))
//
//
//        val adapter = StatesAdapter(data)
//
//        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter
//      adapter.setOnClickedListner(object :StatesAdapter.onItemClickedlistner{
//          override fun onitemClicked(position: Int) {
//              Toast.makeText(this@MainActivity,"you have clicked $position", Toast.LENGTH_SHORT).show()
//
////           val intent  = Intent(this@MainActivity,PlaceActivity::class.java)
////              intent.putExtra("placephotos",data[position].statePic)
////              intent.putExtra("placename",data[position].stateName)
//
//          }

//      })


        setUpRecyclerView()



    }

    private fun setUpRecyclerView() {


        val postCollections = stateCollection
        val query = postCollections.orderBy("stateRating", Query.Direction.ASCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<NewStates>().setQuery(query,NewStates::class.java).build()

        adapter = NewStatesAdapter(recyclerViewOptions,this)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(this,2)
    }
    override fun onStart() {    //We want adapter to listen the changes made in database so we have created onstart function which will start listening once the app start
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {    //and we Adapter to stop listen if app closes
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        TODO("Not yet implemented")
    }
}