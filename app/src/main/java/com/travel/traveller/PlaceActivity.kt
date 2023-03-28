package com.travel.traveller

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.travel.traveller.databinding.ActivityPlaceBinding
import com.google.firebase.firestore.FirebaseFirestore

class PlaceActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPlaceBinding
    var photoarray = ArrayList<String>()
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_place)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

      val  city = intent.getStringExtra("city").toString()
       val  state = intent.getStringExtra("state").toString()
        val place = intent.getStringExtra("place").toString()


        binding.placephotos.setOnClickListener{
//            Toast.makeText(this,city+"   is gooo000000d",Toast.LENGTH_SHORT).show()
//            val intent  = Intent(this,UserPhotosActivity::class.java)
//            startActivity(intent)
        }

        val doc = FirebaseFirestore.getInstance().collection("searchPlaces")
            .document("searchplacesList").collection("commoncollection")
            .document(place)

//        val doc = FirebaseFirestore.getInstance().collection("States")
//            .document(state).collection(city)
//            .document(place)
        doc.get().addOnSuccessListener { document ->
//                 documentSnapshot.getId()
            if(document!=null){

//                Toast.makeText(this,"${document.getString("placeName")} always always clicked,", Toast.LENGTH_SHORT).show()
                   binding.placename.setText(document.getString("placeName"))
//                Glide.with(this)
//                    .load(document.getString("placePic"))
//                    .into(binding.placephotos)
//              ///  External
                val placeslider:ImageSlider = findViewById(R.id.placephotos)





 ///////////////////////////////////
//                For onClick images
//                placeslider.setItemClickListener(object : ItemClickListener {
//                    override fun onItemSelected(position: Int) {
////                        Toast.makeText(this@PlaceActivity,"photo clicked",Toast.LENGTH_SHORT).show()
//                val intent = Intent(this@PlaceActivity,FullPhotoActivity::class.java)
//                intent.putExtra("photoarray",photoarray)
//                startActivity(intent)
//                    }
//                })
////////////////////////////////////

                photoarray = document.get("placePhotos") as ArrayList<String>
                val simplearray = ArrayList<SlideModel>()
                for(i in photoarray){
                    simplearray.add(SlideModel(i))
                }
                placeslider.setImageList(simplearray, ScaleTypes.CENTER_CROP)
                 /////
                document.getDouble("placeRating").toString().also { binding.rating.text = it }
                val rating = document.getDouble("placeRating")
                if (rating != null) {
                    binding.simpleRatingBar.setRating(rating.toFloat())
                }

                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
            }else{
                Log.d(ContentValues.TAG, "No such document")
            }

        }
            .addOnFailureListener{ exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


        binding.userphotos.setOnClickListener{
            val intent  = Intent(this,UserPhotosActivity::class.java)
          intent.putExtra("photoarray",photoarray.toString())
            startActivity(intent)
        }



    }

}