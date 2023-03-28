package com.travel.traveller

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.travel.traveller.Daos.PostDao
import com.travel.traveller.Daos.UserDao
import com.travel.traveller.Model.User
import com.travel.traveller.Model.userphotos
import com.travel.traveller.databinding.ActivityCreatePhotoBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class createPhotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreatePhotoBinding
    lateinit var puri: Uri
    lateinit var postDao: PostDao
    var place = "Nalanda University"
    val db  = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val reviewCollections = db.collection("searchPlaces").document("searchplacesList")
        .collection("commoncollection")
    var downloadUri = ""
    var list:ArrayList<String> = ArrayList()
    var l:ArrayList<String> = ArrayList()
    ////////////
    //store uris of picked images
    private var images: ArrayList<Uri?>? = null
    //current position/index of selected images
    private var position = 0
    //request code to pick image(s)
    private val PICK_IMAGES_CODE = 0
    ////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_photo)
        binding = ActivityCreatePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        place = intent.getStringExtra("place").toString()


        ////////////////////////////////
        //init list for multiple
        images = ArrayList()

        //setup image switcher
        binding.imageSwitcher.setFactory { ImageView(applicationContext) }

        //pick images clicking this button
        binding.pickImagesBtn.setOnClickListener {
            pickImagesIntent()
        }

        //switch to next image clicking this button
        binding.nextBtn.setOnClickListener {
            if (position < images!!.size-1){
                position++
                binding.imageSwitcher.setImageURI(images!![position])
            }
            else{
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }
        }

        //switch to previous image clicking this button
        binding.previousBtn.setOnClickListener {
            if (position > 0){
                position--
                binding.imageSwitcher.setImageURI(images!![position])
            }
            else{
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }

        }
//        binding.post.setOnClickListener {
//
//            val progressDialog = ProgressDialog(this@createPhotoActivity)
//            progressDialog.setTitle("Uploading Please Wait..")
////            progressDialog.setMessage("Application is loading, please wait")
//            progressDialog.show()
//            val formatte = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//            val no = Date()
//            val fileNam = formatte.format(no)
//            val storageRef = FirebaseStorage.getInstance().getReference("imagesNow/$fileNam")
//
//            val uploadTasks = arrayListOf<UploadTask>()
//            for (i in images!!) {
//                val uploadTask = i?.let { storageRef.putFile(it) }
//
//                if (uploadTask != null) {
//                    uploadTasks.add(uploadTask)
//                }
//            }
//
//            var imagelist: ArrayList<String> = ArrayList()
//
//            for (uploadTask in uploadTasks) {
//                val downloadUrlTask = uploadTask.continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        task.exception?.let {
//                            throw it
//                        }
//                    }
//                    storageRef.downloadUrl
//                }.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val downloadUri = task.result
//                        imagelist.add(downloadUri.toString())
//                        // Use the download URL to display or download the image
//                    } else {
//                        // Handle failures
//                    }
//                }
//            }
//            ///////
//            GlobalScope.launch {
//                val currentUserId =
//                    auth.currentUser!!.uid  //Here we are getting Id of currentUser and (!!) means if it has not data of current user then get id of user and it will cause Null Pointer Exception and App will crash
//                val userDao = UserDao()
//                val user = userDao.getUserById(currentUserId).await()
//                    .toObject(User::class.java)!!  //By this we are getting user information by its id by currentUserId
//                //Using getUserById method but it will return as a task .So we are parsing into User object using toObject
//                val currentTime =
//                    System.currentTimeMillis() //Here we are getting Post time in long form
////        val list:ArrayList<String> = ArrayList()
////        list.add(downloadUri)
//
//
//                val post = userphotos(imagelist, user, currentTime, 0)
//                reviewCollections.document(place).collection("userphotocollection").document()
//                    .set(post)
//                binding.imageET.setImageURI(null)
//            progressDialog.dismiss()
//            }
//            Log.d("inside the post", imagelist.toString())
//        }
//////////////////////////////////////////




        val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
            binding.imageET .setImageURI(uri)    // Handle the returned Uri
            if (uri != null) {
                puri = uri
            }
        }
        binding.choose.setOnClickListener {
//            selectimage()
            getContent.launch("image/*")
        }
        binding.post.setOnClickListener {

            val progressDialog = ProgressDialog(this@createPhotoActivity)
            progressDialog.setTitle("Uploading Please Wait..")
//            progressDialog.setMessage("Application is loading, please wait")
            progressDialog.show()



            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now =Date()
            val fileName =formatter.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
           val dUrl =  storageReference.downloadUrl.toString()
            ///////between

            val uploadTask= storageReference.putFile(puri)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result.toString()
//                    Toast.makeText(this,"it happened $downloadUri ",Toast.LENGTH_SHORT).show()
                    list.add(downloadUri)
                    addElement(downloadUri)
                    Log.d("after adding", list.toString())
                    //                ////

                GlobalScope.launch {
                    val currentUserId = auth.currentUser!!.uid  //Here we are getting Id of currentUser and (!!) means if it has not data of current user then get id of user and it will cause Null Pointer Exception and App will crash
                    val userDao = UserDao()
                    val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!  //By this we are getting user information by its id by currentUserId
                    //Using getUserById method but it will return as a task .So we are parsing into User object using toObject
                    val currentTime = System.currentTimeMillis() //Here we are getting Post time in long form
//        val list:ArrayList<String> = ArrayList()
//        list.add(downloadUri)


                    val post = userphotos(list,user, currentTime,0)
                    reviewCollections.document(place).collection("userphotocollection").document().set(post)
                    binding.imageET .setImageURI(null)

                    progressDialog.dismiss()
                }
                    Toast.makeText(this,"Uploaded Successfully!",Toast.LENGTH_SHORT).show()
                ////
                } else {
                    progressDialog.dismiss()
                    // Handle failures
                    // ...
                }
//                Log.d("inside the post", list.toString())

                l.addAll(list)
                Log.d("get the post", l.toString())

            }    ///copy this

        } //here where post btn setonclick ends
        Log.d("out the post", l.toString())

    }

    private fun addElement(downloadUri: String) {
//          l.add(downloadUri)
    }


    //////////////////////////////////////////////
    private fun pickImagesIntent(){
        val  intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE)
    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == PICK_IMAGES_CODE){

        if (resultCode == Activity.RESULT_OK){

            if (data!!.clipData != null){
                //picked multiple images
                //get number of picked images
                val count = data.clipData!!.itemCount
                for (i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    //add image to list
                    images!!.add(imageUri)
                }
                //set first image from list to image switcher
                binding.imageSwitcher.setImageURI(images!![0])
                position = 0
            }else{
                //picked single image
                val imageUri = data.data
                //set image to image switcher
                binding.imageSwitcher.setImageURI(imageUri)
                position = 0
            }

        }

    }
}
   ////////////////////////////////////
}