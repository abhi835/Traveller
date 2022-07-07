package com.example.traveller.Daos


import com.example.traveller.Model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    @OptIn(DelicateCoroutinesApi::class)
    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                usersCollection.document(user.uid).set(it)
            }

        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {    //Here it will take user id using uId and return User total information in form of Task
        return usersCollection.document(uId).get()
    }

}