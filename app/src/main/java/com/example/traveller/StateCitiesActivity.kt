package com.example.traveller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveller.Adapter.IstateCitiesAdapter
import com.example.traveller.Adapter.StateCitiesAdapter
import com.example.traveller.Model.StateCities
import com.example.traveller.databinding.ActivityStateCitiesBinding
import com.google.firebase.firestore.FirebaseFirestore

class StateCitiesActivity : AppCompatActivity(), IstateCitiesAdapter {
//  lateinit var  newarray: ArrayList<String>
    lateinit var stateCities : Array<StateCities>
    var stateid = "Bihar"
    private lateinit var binding:ActivityStateCitiesBinding
//    private lateinit var adapter: StateCitiesAdapter
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_state_cities)
        binding = ActivityStateCitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val postId = intent.getStringExtra("stateId")
        if (postId != null) {
            stateid = postId

            val doc = FirebaseFirestore.getInstance().collection("States").document(postId)
//            doc.get().addOnSuccessListener {document ->
//              if(document!=null){
//                  var photoarray = ArrayList<String>()
//                  photoarray = document.get("stateCities")
////                  stateCities = photoarray.toTypedArray()
////                  Toast.makeText(this,photoarray.toString()+"is clicking",Toast.LENGTH_SHORT).show()
////                  for(s in photoarray){
////                      newarray.add(s)
////                  }
////                  Toast.makeText(this,newarray.toString()+"is clicking",Toast.LENGTH_SHORT).show()
//              }
//            }


        }
        /////////



      //////////////
        if(postId=="Bihar") {
            stateCities = arrayOf(
                StateCities("Nalanda"),
                StateCities("Patna"),
                StateCities("Rajgir"),
                StateCities("Vaishali")
//          "Nalanda", "Patna",
//           "Rajgir", "Vaishali"

            )
        }else if(postId=="Goa"){
//            Toast.makeText(this,"Fuck Clicked Clicked Clicked Clicked Clicked", Toast.LENGTH_SHORT).show()
            stateCities = arrayOf(
                StateCities("Goa"),StateCities("GFo"),StateCities("Goa"),StateCities("Goa")
//                "Goa","GFo","Goa","Goa","Goa","Goa"
            )
        }else if(postId=="Uttarakhand"){
            stateCities = arrayOf(
                StateCities("Mejoram"),StateCities("Mejoram"),StateCities("Mejoram"),StateCities("Mejoram")
//                "Mejoram","Mejoram","Mejoram","Mejoram","Mejoram","Mejoram"
            )
        }else if(postId=="West Bengal"){
            stateCities = arrayOf(
                StateCities("Kolkata"),StateCities("Kolkata"),StateCities("Kolkata"),StateCities("Kolkata")
//                "Kolkata","Kolkata","Kolkata","Kolkata","Kolkata","Kolkata"
            )
        }

        binding.statecititesrecyclerview.layoutManager = LinearLayoutManager(this)
        val Cities =fetchData()
//        val Cities = newarray
        val adapter =StateCitiesAdapter(Cities,this)
        binding.statecititesrecyclerview.adapter = adapter

    }

    private fun fetchData(): ArrayList<String>{
      val list = ArrayList<String>()
        for( i in stateCities.indices){
            list.add(stateCities[i].stateCities)
        }
        return list
    }

    override fun onStateCitiesClicked(item: String) {
                Toast.makeText(this,"$item Clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,CityPlacesActivity::class.java)
        intent.putExtra("CityId",item)
        intent.putExtra("Stateid",stateid)
        startActivity(intent)
    }

}