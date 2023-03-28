package com.travel.traveller.Daos

import com.travel.traveller.Model.User
import com.travel.traveller.Model.location
import com.travel.traveller.Model.review
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
val db  =FirebaseFirestore.getInstance()
    val auth = Firebase.auth



    val reviewCollections = db.collection("searchPlaces").document("searchplacesList")
        .collection("commoncollection")


    fun addPost(text: String,place:String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid  //Here we are getting Id of currentUser and (!!) means if it has not data of current user then get id of user and it will cause Null Pointer Exception and App will crash
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!  //By this we are getting user information by its id by currentUserId
            //Using getUserById method but it will return as a task .So we are parsing into User object using toObject
            val currentTime = System.currentTimeMillis()         //Here we are getting Post time in long form
            val post = location(text, user, currentTime,0)
            reviewCollections.document(place).collection("locationcollection").document().set(post)
        }
    }

    private fun getPostById(postId:String,place:String): Task<DocumentSnapshot> {
        return reviewCollections.document(place).collection("locationcollection").document(postId).get()
    }

    fun upgradevotes(postId: String,place:String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId,place).await().toObject(location::class.java)!!
            val isvoted = post.votedBy.contains(currentUserId)      // It checks whether in likedBy array the current user is present

            if(isvoted){                //If likedBy array has current user remove the current user from array
                post.votedBy.remove(currentUserId)
           reviewCollections.document(place).collection("locationcollection")
                    .document(postId).update("noofvotes",FieldValue.increment(-1))
//                noofvotesup.update("noofvotes",FieldValue.increment(-1))
            }else{
                post.votedBy.add(currentUserId)     //else add user's id in likedBy array
            reviewCollections.document(place).collection("locationcollection")
                    .document(postId).update("noofvotes",FieldValue.increment(1))
//                noofvotesup.update("noofvotes",FieldValue.increment(1))
            }
            reviewCollections.document(place).collection("locationcollection").document(postId).set(post)    //Now it will save post  in database in postcollection node

        }
    }
    fun downgradevotes(postId: String,place:String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId,place).await().toObject(location::class.java)!!
            val isvoted = post.votedBy.contains(currentUserId)      // It checks whether in likedBy array the current user is present

            if(isvoted){                //If likedBy array has current user remove the current user from array
                post.votedBy.remove(currentUserId)
                val noofvotesup =    reviewCollections.document(place).collection("locationcollection")
                    .document(postId)
                noofvotesup.update("noofvotes",FieldValue.increment(1))
            }else{
                post.votedBy.add(currentUserId)     //else add user's id in likedBy array
                val noofvotesup =    reviewCollections.document(place).collection("locationcollection")
                    .document(postId)
                noofvotesup.update("noofvotes",FieldValue.increment(-1))
            }
            reviewCollections.document(place).collection("locationcollection").document(postId).set(post)    //Now it will save post  in database in postcollection node

        }
    }




    fun addreview(text: String,place:String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid  //Here we are getting Id of currentUser and (!!) means if it has not data of current user then get id of user and it will cause Null Pointer Exception and App will crash
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!  //By this we are getting user information by its id by currentUserId
            //Using getUserById method but it will return as a task .So we are parsing into User object using toObject
            val currentTime = System.currentTimeMillis()         //Here we are getting Post time in long form
            val post = review(text, user, currentTime,0)
            reviewCollections.document(place).collection("reviewcollection").document().set(post)
        }
    }
    private fun getreviewPostById(postId:String,place:String): Task<DocumentSnapshot> {
        return reviewCollections.document(place).collection("reviewcollection").document(postId).get()
    }


    fun upgradereviewvotes(postId: String,place:String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getreviewPostById(postId,place).await().toObject(review::class.java)!!
            val isvoted = post.votedBy.contains(currentUserId)      // It checks whether in likedBy array the current user is present

            if(isvoted){                //If likedBy array has current user remove the current user from array
                post.votedBy.remove(currentUserId)
                val noofvotesup =    reviewCollections.document(place).collection("reviewcollection")
                    .document(postId)
                noofvotesup.update("noofvotes",FieldValue.increment(-1))
            }else{
                post.votedBy.add(currentUserId)     //else add user's id in likedBy array
                val noofvotesup =    reviewCollections.document(place).collection("reviewcollection")
                    .document(postId)
                noofvotesup.update("noofvotes",FieldValue.increment(1))
            }
            reviewCollections.document(place).collection("reviewcollection").document(postId).set(post)    //Now it will save post  in database in postcollection node

        }
    }
    fun downgradereviewvotes(postId: String,place:String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getreviewPostById(postId,place).await().toObject(review::class.java)!!
            val isvoted = post.votedBy.contains(currentUserId)      // It checks whether in likedBy array the current user is present

            if(isvoted){                //If likedBy array has current user remove the current user from array
                post.votedBy.remove(currentUserId)
                val noofvotesup =    reviewCollections.document(place).collection("reviewcollection")
                    .document(postId)
                noofvotesup.update("noofvotes",FieldValue.increment(1))
            }else{
                post.votedBy.add(currentUserId)     //else add user's id in likedBy array
                val noofvotesup =    reviewCollections.document(place).collection("reviewcollection")
                    .document(postId)
                noofvotesup.update("noofvotes",FieldValue.increment(-1))
            }
            reviewCollections.document(place).collection("reviewcollection").document(postId).set(post)    //Now it will save post  in database in postcollection node

        }
    }


//fun addphoto(url:String,place:String){
//    GlobalScope.launch {
//        val currentUserId = auth.currentUser!!.uid  //Here we are getting Id of currentUser and (!!) means if it has not data of current user then get id of user and it will cause Null Pointer Exception and App will crash
//        val userDao = UserDao()
//        val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!  //By this we are getting user information by its id by currentUserId
//        //Using getUserById method but it will return as a task .So we are parsing into User object using toObject
//        val currentTime = System.currentTimeMillis() //Here we are getting Post time in long form
////        val list:ArrayList<String> = ArrayList()
////        list.add(url)
//        val post = userphotos(url,user, currentTime,0)
//        reviewCollections.document(place).collection("userphotocollection").document().set(post)
//    }
//}



}