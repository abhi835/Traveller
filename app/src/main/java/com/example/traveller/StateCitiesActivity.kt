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
                StateCities("Vaishali"),
                        StateCities("Gaya")


            )
        }else if(postId=="Goa"){
//            Toast.makeText(this,"Fuck Clicked Clicked Clicked Clicked Clicked", Toast.LENGTH_SHORT).show()
            stateCities = arrayOf(
                StateCities("Goa")
//                "Goa","GFo","Goa","Goa","Goa","Goa"
            )
        }else if(postId=="West Bengal"){
            stateCities = arrayOf(
                StateCities("Kolkata"),StateCities("Darjeeling"),StateCities("South 24 Parganas district"),StateCities("Siliguri")
//                "Kolkata","Kolkata","Kolkata","Kolkata","Kolkata","Kolkata"1
            )
        }else if(postId=="Uttar Pradesh"){
            stateCities = arrayOf(
                StateCities("Agra"),StateCities("Varanasi"),StateCities("Lucknow"),StateCities("Noida"),StateCities("Mathura"),StateCities("Kanpur")
//
            )
        }else if(postId=="Tamil Nadu"){
            stateCities = arrayOf(
                StateCities("Ooty"),StateCities("Madurai"),StateCities("Kodaikanal"),StateCities("Chennai"),StateCities("Rameshwaram")
                ,StateCities("Kanyakumari"),StateCities("Tiruchirappalli"),StateCities("Rameshwaram"),StateCities("Mahabalipuram"),StateCities("Kanchipuram")
//
            )
        }else if(postId=="Uttarakhand"){
            stateCities = arrayOf(
                StateCities("Rishikesh"),StateCities("Haridwar"),StateCities("Kedarnath"),StateCities("Badrinath")
                ,StateCities("Dehradun"),StateCities("Nainital"),StateCities("Auli"),StateCities("Mussoorie")
//
            )
        }else if(postId=="Delhi"){
            stateCities = arrayOf(
                StateCities("Delhi")
//
            )
        }else if(postId=="Himachal Pradesh"){
            stateCities = arrayOf(
                StateCities("Manali"),StateCities("Shimla"),StateCities("Dharamshala"),StateCities("Khajjiar")
                ,StateCities("Dalhousie"),StateCities("Palampur"),StateCities("Chail"),StateCities("Kufri")
//
            )
        }else if(postId=="Ladakh"){
        stateCities = arrayOf(
            StateCities("Ladakh")
//
        )
    }else if(postId=="Jammu & Kashmir"){
            stateCities = arrayOf(
                StateCities("Srinagar"),StateCities("Katra"),StateCities("Jammu"),StateCities("Gulmarg"),StateCities("Pahalgam")

//
            )
        }else if(postId=="Jharkhand"){
            stateCities = arrayOf(
                StateCities("Ranchi"),StateCities("Deoghar"),StateCities("Jamshedpur")

//
            )
        }else if(postId=="Rajasthan"){
            stateCities = arrayOf(
                StateCities("Jaipur"),StateCities("Udaipur"),StateCities("Jaisalmer"),StateCities("Jodhpur")
                ,StateCities("Bikaner"),StateCities("Mount Abu"),StateCities("Chittorgarh")

//
            )
        }else if(postId=="Gujarat"){
            stateCities = arrayOf(
                StateCities("Ahmedabad"),StateCities("Kutch"),StateCities("Somnath")
                ,StateCities("Gandhinagar"),StateCities("Diu"),StateCities("Surat")
                ,StateCities("Vadodara"),StateCities("Kevadia"),StateCities("Dwarka")

//
            )
        }else if(postId=="Maharashtra"){
            stateCities = arrayOf(
                StateCities("Mumbai"),StateCities("Pune"),StateCities("Shirdi"),
                StateCities("Aurangabad"),
                StateCities("Mahabaleshwar")

//
            )
        }else if(postId=="Odisha"){
            stateCities = arrayOf(
                StateCities("Puri"),StateCities("Bhubaneswar")
            )
        }else if(postId=="Madhya Pradesh"){
            stateCities = arrayOf(
                StateCities("Khajuraho"),StateCities("Bhopal"),StateCities("Indore")
                ,StateCities("Gwalior"),StateCities("Ujjain"),StateCities("Jabalpur")
            )
        }else if(postId=="Assam"){
            stateCities = arrayOf(
                StateCities("Guwahati"),StateCities("Golaghat")
            )
        }else if(postId=="Karnataka"){
            stateCities = arrayOf(
                StateCities("Coorg"),StateCities("Mysore"),StateCities("Hampi")
                ,StateCities("Bengaluru")
            )
        }else if(postId=="Andhra Pradesh"){
            stateCities = arrayOf(
                StateCities("Visakhapatnam"),StateCities("Araku Valley")
                ,StateCities("Tirupati"),StateCities("Amaravati")
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
//                Toast.makeText(this,"$item Clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,CityPlacesActivity::class.java)
        intent.putExtra("CityId",item)
        intent.putExtra("Stateid",stateid)
        startActivity(intent)
    }

}