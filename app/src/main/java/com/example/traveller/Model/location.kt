package com.example.traveller.Model

data class location (
        val text:String = "",
        val createdBy:User = User(),                 //Here we have created who have created the post
        val createdAt:Long= 0L,
//    val optionBy:ArrayList<String> = ArrayList(),
        val noofvotes:Int = 0,
        val votedBy:ArrayList<String> = ArrayList()    //Here we are adding User's id in arraylist who have liked the post
        )
