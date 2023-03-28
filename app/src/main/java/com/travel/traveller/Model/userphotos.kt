package com.travel.traveller.Model

data class userphotos(
    var userPhotos:List<String> = emptyList(),
//    val userPhotos:String ="",
    val createdBy:User = User(),                 //Here we have created who have created the post
    val createdAt:Long= 0L,
//    val optionBy:ArrayList<String> = ArrayList(),
    val noofvotes:Int = 0,
    val votedBy:ArrayList<String> = ArrayList()    //
)
//{
//    constructor(): this("", "")
//}
