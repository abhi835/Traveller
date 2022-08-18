package com.example.traveller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveller.Adapter.IPostAdapter
import com.example.traveller.Adapter.IstateCitiesAdapter
import com.example.traveller.Adapter.StateCitiesAdapter
import com.example.traveller.databinding.ActivityStateCitiesBinding
import com.google.firebase.firestore.FirebaseFirestore

class StateCitiesActivity : AppCompatActivity(), IstateCitiesAdapter {

    lateinit var stateCities : Array<String>
     var stateid = "Bihar"
    private lateinit var binding:ActivityStateCitiesBinding
//    private lateinit var adapter: StateCitiesAdapter
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_state_cities)
        binding = ActivityStateCitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getStringExtra("stateId")
        if (postId != null) {
            stateid = postId
        }

       stateCities = arrayOf(
          "Nalanda", "Patna",
           "Rajgir", "Vaishali"

        )

        if(postId=="Goa"){
//            Toast.makeText(this,"Fuck Clicked Clicked Clicked Clicked Clicked", Toast.LENGTH_SHORT).show()
            stateCities = arrayOf(
                "Goa","GFo","Goa","Goa","Goa","Goa"
            )
        }else if(postId=="Uttarakhand"){
            stateCities = arrayOf(
                "Mejoram","Mejoram","Mejoram","Mejoram","Mejoram","Mejoram"
            )
        }else if(postId=="West Bengal"){
            stateCities = arrayOf(
                "Kolkata","Kolkata","Kolkata","Kolkata","Kolkata","Kolkata"
            )
        }

        binding.statecititesrecyclerview.layoutManager = LinearLayoutManager(this)
        val Cities =fetchData()
        val adapter =StateCitiesAdapter(Cities,this)
        binding.statecititesrecyclerview.adapter = adapter

    }

    private fun fetchData(): ArrayList<String>{
      val list = ArrayList<String>()
        for( i in stateCities.indices){
            list.add(stateCities[i])
        }
        return list
    }

    override fun onStateCitiesClicked(cityId: String) {
                Toast.makeText(this,"$cityId Clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,CityPlacesActivity::class.java)
        intent.putExtra("CityId",cityId)
        intent.putExtra("Stateid",stateid)
        startActivity(intent)
    }

}