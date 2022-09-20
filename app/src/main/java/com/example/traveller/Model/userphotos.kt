package com.example.traveller.Model

data class userphotos(
    val placeName:String = "",
    val placePic:String ="",
    val placeRating:Int = 0,
    val placePhotos:Array<String>,
    val description:String = "",
    val location:String
)
