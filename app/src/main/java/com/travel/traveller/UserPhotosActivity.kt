package com.travel.traveller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.travel.traveller.Adapter.userphotosAdapter
import com.travel.traveller.Daos.PostDao
import com.travel.traveller.Model.userphotos
import com.travel.traveller.databinding.ActivityUserPhotoBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage


class UserPhotosActivity : AppCompatActivity(), userphotosAdapter.IUserPhotosAdapter {
    var place = "Nalanda University"
    private lateinit var postDao: PostDao
    private lateinit var adapter:userphotosAdapter
    private lateinit var binding: ActivityUserPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_full_photo)
        binding = ActivityUserPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.userphotorecyclerview.itemAnimator = null
        place = intent.getStringExtra("place").toString()

        binding.photobotton.setOnClickListener{
            val intent  = Intent(this,createPhotoActivity::class.java)
            intent.putExtra("place",place)
            startActivity(intent)
        }

        setUpRecyclerView()

    }
   private fun setUpRecyclerView(){
       postDao = PostDao()
            val br  = FirebaseStorage.getInstance().getReference( )
       val postCollections = postDao.db.collection("searchPlaces").document("searchplacesList")
            .collection("commoncollection").document(place).collection("userphotocollection")
        val query = postCollections.orderBy("createdAt", Query.Direction.DESCENDING)      //Here we are getting Query from PostDao and we will feed the data into adapter we have also sorted out posts on the basis of "created AT" which is time and it will show newest posts at first in recyclerView
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<userphotos>().setQuery(query,
            userphotos::class.java).build()

        adapter = userphotosAdapter(recyclerViewOptions,this)

        binding.userphotorecyclerview.adapter = adapter
        binding.userphotorecyclerview.layoutManager = LinearLayoutManager(this)
    }
    override fun onStart() {    //We want adapter to listen the changes made in database so we have created onstart function which will start listening once the app start
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {    //and we Adapter to stop listen if app closes
        super.onStop()
        adapter.stopListening()
    }
}