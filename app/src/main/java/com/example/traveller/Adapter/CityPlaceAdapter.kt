package com.example.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.traveller.Model.CityPlaces
import com.example.traveller.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CityPlaceAdapter(options: FirestoreRecyclerOptions<CityPlaces>,val listener:ICityPlacesAdapter):
    FirestoreRecyclerAdapter<CityPlaces, CityPlaceAdapter.PostViewHolder>(options) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cityPlacepic: ImageView = itemView.findViewById(R.id.CityPlacessImage)
        val placeName: TextView = itemView.findViewById(R.id.CityPlacessTitle)
        val placeRating: TextView = itemView.findViewById(R.id.CityPlacessRating)
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PostViewHolder {
        val viewholder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cityplaces, parent, false))
        viewholder.itemView.setOnClickListener{
            listener.CityPlaceClicked(snapshots.getSnapshot(viewholder.adapterPosition).id)
        }

        return viewholder
    }



    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: CityPlaces) {
      holder.placeRating.text = model.placeRating.toString()
        holder.placeName.text = model.placeName
        Glide.with(holder.cityPlacepic.context).load(model.placePic).into(holder.cityPlacepic)
    }

}
interface ICityPlacesAdapter{
    fun CityPlaceClicked(postId:String)
}