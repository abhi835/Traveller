package com.example.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.traveller.MainActivity
import com.example.traveller.Model.NewStates
import com.example.traveller.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class NewStatesAdapter(options: FirestoreRecyclerOptions<NewStates>,val listener:IPostAdapter) : FirestoreRecyclerAdapter<NewStates, NewStatesAdapter.PostViewHolder>(
    options
) {


    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
     val statepic:ImageView = itemView.findViewById(R.id.StateImage)
        val statename:TextView = itemView.findViewById(R.id.StateTitle)
        val stateRating:TextView = itemView.findViewById(R.id.stateRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PostViewHolder {
                 val viewholder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gridview_item,parent,false))
        return viewholder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: NewStates) {
         holder.statename.text = model.stateName
        Glide.with(holder.statepic.context).load(model.statePic).into(holder.statepic)
        holder.stateRating.text = model.stateRating.toString()


    }
}
interface IPostAdapter{
    fun onLikeClicked(postId:String)
}