package com.travel.traveller

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.travel.traveller.databinding.ActivityPlaceSecondBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat

class PlaceActivitySecond : AppCompatActivity() {
    lateinit var binding:ActivityPlaceSecondBinding
    var photoarray = ArrayList<String>()
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_place_second)
        binding = ActivityPlaceSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val  place = intent.getStringExtra("place").toString()

        binding.placephotos.setOnClickListener{
//            Toast.makeText(this,place+"   is gooo000000d", Toast.LENGTH_SHORT).show()
//            val intent  = Intent(this,UserPhotosActivity::class.java)vZ
//            startActivity(intent)
        }

        val doc = FirebaseFirestore.getInstance().collection("searchPlaces")
            .document("searchplacesList").collection("commoncollection")
            .document(place)
        doc.get().addOnSuccessListener { document ->
//                 documentSnapshot.getId()
            if(document!=null){

//                Toast.makeText(this,"${document.getString("placeName")} always always clicked,", Toast.LENGTH_SHORT).show()
                binding.placename.setText(document.getString("placeName"))
                binding.description.setText(document.getString("placeDescription"))
                  val k = document.getLong("ratingNumber")
                val formatter = DecimalFormat("#,###")
                val formatted: String = formatter.format(k)
                binding.ratingnumber.text = "($formatted)"
//                document.getLong("ratingNumber").toString().also { binding.ratingnumber.text =
//                    "($it)"
//                }

//                Glide.with(this)
//                    .load(document.getString("placePic"))
//                    .into(binding.placephotos)
//              ///  External
                val placeslider: ImageSlider = findViewById(R.id.placephotos)





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
                placeslider.setImageList(simplearray, ScaleTypes.CENTER_INSIDE)
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
            intent.putExtra("place",place)
            startActivity(intent)
        }

        binding.howtogo.setOnClickListener {
            val intent = Intent(this,locationActivity::class.java)
            intent.putExtra("place",place)
            startActivity(intent)
        }

         binding.usersreview.setOnClickListener {
             val intent = Intent(this,reviewActivity::class.java)
             intent.putExtra("place",place)
             startActivity(intent)
         }

    }


}