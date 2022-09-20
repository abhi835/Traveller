package com.example.traveller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.traveller.Adapter.CityPlaceAdapter
import com.example.traveller.Adapter.ICityPlacesAdapter
import com.example.traveller.Model.CityPlaces
import com.example.traveller.databinding.ActivityCityPlacesBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CityPlacesActivity : AppCompatActivity(), ICityPlacesAdapter {
    private lateinit var binding:ActivityCityPlacesBinding
    private lateinit var adapter:CityPlaceAdapter
    val db = FirebaseFirestore.getInstance()
var StatecitiesCollection = db.collection("States")
    var cityName = ""
    var stateName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_city_places)
        binding = ActivityCityPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        cityName = intent.getStringExtra("CityId").toString()
        stateName = intent.getStringExtra("Stateid").toString()
//       StatecitiesCollection = stateName?.let { cityName?.let { it1 ->
//           db.collection("States").document(it).collection(
//               it1
//           )
//       } }!!
//        StatecitiesCollection = db.collection("States").document(stateName).collection(cityName)
        ////
        StatecitiesCollection = db.collection("searchPlaces")
            .document("searchplacesList").collection("commoncollection")
        ////
//      val  StatecitiesCollection = db.collection("States").document("Bihar")

//       val doc = FirebaseFirestore.getInstance().collection("States")
//            .document(stateName.toString()).collection("Nalanda")
//            .document("Nalanda University")
//        doc.get().addOnSuccessListener { document ->
////                 documentSnapshot.getId()
//            if(document!=null){
////                Toast.makeText(this,"${document.getString("placeName")} always clicked,", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//            }else{
//                Log.d(TAG, "No such document")
//            }
//
//            }
//            .addOnFailureListener{ exception ->
//            Log.d(TAG, "get failed with ", exception)
//        }

        /////
//        Toast.makeText(this,"${StatecitiesCollection.toString()} always clicked", Toast.LENGTH_SHORT).show()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
             val postCollection = StatecitiesCollection
        val query =postCollection.whereEqualTo("placeCity",cityName)
            .orderBy("placeRating", Query.Direction.ASCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions =
            FirestoreRecyclerOptions.Builder<CityPlaces>().setQuery(query,CityPlaces::class.java).build()


        adapter = CityPlaceAdapter(recyclerViewOptions,this)

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
    override fun CityPlaceClicked(postId: String) {
//        val stateid = getStateById(postId)
        Toast.makeText(this,"$postId place Clicked",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,PlaceActivitySecond::class.java)
        intent.putExtra("place",postId)
        intent.putExtra("city",cityName)
        intent.putExtra("state",stateName)
        startActivity(intent)

//        stateCollection.document(postId).collection("Nalanda")

    }


}