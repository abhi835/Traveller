package com.travel.traveller


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.travel.traveller.Daos.UserDao
import com.travel.traveller.Model.User
import com.travel.traveller.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private val RC_SIGN_IN: Int = 123
    private val TAG = "SignInActivity Tag"
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var eauth: FirebaseAuth
    var email = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        auth = Firebase.auth

        binding.btngoogle.setOnClickListener{
            signIn()
        }
//        binding.btnSignIn.setOnClickListener{
//          email =binding.etEmail.text.toString()
//            password = binding.etpassword.text.toString()
//              if(email!=null&&password!=null){
//
//                  auth.signInWithEmailAndPassword(email, password)
//                      .addOnCompleteListener(this) { task ->
//                          if (task.isSuccessful) {
//                              // Sign in success, update UI with the signed-in user's information
//                              Log.d(TAG, "signInWithEmail:success")
//                              val user = auth.currentUser
//                              updateUI(user)
//                          } else {
//                              // If sign in fails, display a message to the user.
//                              Log.w(TAG, "signInWithEmail:failure", task.exception)
//                              Toast.makeText(baseContext, "Authentication failed.",
//                                  Toast.LENGTH_SHORT).show()
//                              updateUI(null)
//                          }
//                      }
//              }else{
//                  Toast.makeText(this,"Username or passowrd is missing!",Toast.LENGTH_SHORT).show()
//              }
//
//        }


    }
    override fun onStart() {          //On start function called just after onCreate method
        super.onStart()
        val currentUser = auth.currentUser    //here it get currentUser from auth class and it will call updateUI function
        updateUI(currentUser)
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)    //it opens the dialog of emails
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {    //when you come back after google signin
        super.onActivityResult(requestCode, resultCode, data)                            //it tells activity about signin

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {   //it handles the google account
        try {
            val account =
                completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)

            firebaseAuthWithGoogle(account.idToken!!)   //here the token gives the user's name photo email
            Toast.makeText(this,"Please wait for few seconds..",Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        binding.signInbutton.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {                             //this is courutiene
            val auth = auth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main) {       //it will allow to come in main thread after background work
                updateUI(firebaseUser)
            }
        }

    }
    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {              //it means if app gets firebaseUser from email

            val user = User(firebaseUser.uid,firebaseUser.displayName,firebaseUser.photoUrl.toString())
            val userDao = UserDao()
            userDao.addUser(user)

            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        } else {
//            binding.signInbutton.visibility = View.VISIBLE
            binding.progressbar.visibility = View.GONE
        }
    }
}