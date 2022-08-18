package com.example.traveller

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.traveller.databinding.ActivityPlaceBinding
import com.google.firebase.firestore.FirebaseFirestore

class PlaceActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPlaceBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_place)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

      val  city = intent.getStringExtra("city").toString()
       val  state = intent.getStringExtra("state").toString()
        val place = intent.getStringExtra("place").toString()



        val doc = FirebaseFirestore.getInstance().collection("States")
            .document(state).collection(city)
            .document(place)
        doc.get().addOnSuccessListener { document ->
//                 documentSnapshot.getId()
            if(document!=null){
                Toast.makeText(this,"${document.getString("placeName")} always always clicked,", Toast.LENGTH_SHORT).show()
                   binding.placename.setText(document.getString("placeName"))
                Glide.with(this)
                    .load(document.getString("placePic"))
                    .into(binding.placephotos)
                binding.rating.setText(document.getDouble("placeRating").toString())
                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
            }else{
                Log.d(ContentValues.TAG, "No such document")
            }

        }
            .addOnFailureListener{ exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }



    }
}