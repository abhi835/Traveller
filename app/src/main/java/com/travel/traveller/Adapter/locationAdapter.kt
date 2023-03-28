package com.travel.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.traveller.R

import com.travel.traveller.Model.location
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.github.marlonlom.utilities.timeago.TimeAgo

class locationAdapter(options: FirestoreRecyclerOptions<location>, val listener:ILocationAdapter) :
    FirestoreRecyclerAdapter<location, locationAdapter.PostViewHolder>(
    options
){

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val reviewText: TextView =itemView.findViewById(R.id.reviewTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val upvoteButton: ImageView = itemView.findViewById(R.id.upvoteButton)
        val downvoteButton: ImageView = itemView.findViewById(R.id.downvoteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_location,parent,false))
        viewHolder.upvoteButton.setOnClickListener{
            listener.onVoteClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)   //If like clicked listner will know by that
        }
        viewHolder.downvoteButton.setOnClickListener{
            listener.onVotedownClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)   //If like clicked listner will know by that
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: location) {
        holder.reviewText.text = model.text
        holder.userText.text = model.createdBy.displayName
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.votedBy.size.toString()
        holder.createdAt.text = TimeAgo.using(model.createdAt)

    }

    interface ILocationAdapter{
        fun onVoteClicked(postId:String)
        fun onVotedownClicked(postId: String)
    }
}

