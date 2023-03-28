package com.travel.traveller

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travel.traveller.Adapter.onitemClicked
import com.travel.traveller.Adapter.searchplaceAdapter
import com.travel.traveller.Model.searchplace
import com.travel.traveller.databinding.ActivitySearchplaceBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class searchplaceActivity : AppCompatActivity() , onitemClicked {
    // creating variables for
    // our ui components.
    private lateinit var courseRV: RecyclerView
    // variable for our adapter
    // class and array list
    private lateinit var adapter: searchplaceAdapter
    private lateinit var courseModelArrayList: ArrayList<searchplace>
    private lateinit var binding:ActivitySearchplaceBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivitySearchplaceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourses)


        binding.insearchview.isIconified = false
        // calling method to
        // build recycler view.
        buildRecyclerView()
    }

    // calling on create option menu
    // layout to inflate our menu file.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
//        val inflater = menuInflater
//
//        // inside inflater we are inflating our menu file.
//        inflater.inflate(R.menu.search_menu, menu)
//
//        // below line is to get our menu item.
//        val searchItem = menu.findItem(R.id.actionSearch)
//
//        // getting search view of our item-+-.
////        val searchView = searchItem.actionView
//        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        binding.insearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<searchplace>()

        // running a for loop to compare elements.
        for (item in courseModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.

            if (item.searchplacename.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }

    private fun buildRecyclerView() {

        // below line we are creating a new array list
        courseModelArrayList = ArrayList<searchplace>()

       val doc = db.collection("searchPlaces").document("searchplacesList")
        doc.get().addOnSuccessListener { document ->
//                 documentSnapshot.getId()
            if(document!=null){
                    val ArrayList:ArrayList<String> = document.get("searchplacesList") as ArrayList<String>
                for(i in ArrayList){
                    courseModelArrayList.add(searchplace(i))
                }
            }else{
                Log.d(ContentValues.TAG, "No such document")
            }

        }
            .addOnFailureListener{ exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }



        // below line is to add data to our array list.
//        courseModelArrayList.add(searchplace("Delhi City"))
//        courseModelArrayList.add(searchplace("Agra"))


        // initializing our adapter class.

        adapter = searchplaceAdapter(courseModelArrayList,this)

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(this)
        courseRV.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        courseRV.layoutManager = manager

        // setting adapter to
        // our recycler view.
        courseRV.adapter = adapter


    }

    override fun onitemClicked(item: searchplace) {
        Toast.makeText(this,"${item.searchplacename}  clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,PlaceActivitySecond::class.java)
        intent.putExtra("place",item.searchplacename)

        startActivity(intent)
    }
}