package com.travel.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.traveller.Model.review
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.travel.traveller.R

class reviewAdapter(options: FirestoreRecyclerOptions<review>, val listener:IReviewAdapter) :
    FirestoreRecyclerAdapter<review, reviewAdapter.PostViewHolder>(
        options
    ){

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val reviewText: TextView =itemView.findViewById(R.id.reviewText)
        val userText: TextView = itemView.findViewById(R.id.reviewuserName)
        val createdAt: TextView = itemView.findViewById(R.id.reviewcreatedAt)
        val likeCount: TextView = itemView.findViewById(R.id.reviewlikeCount)
        val userImage: ImageView = itemView.findViewById(R.id.reviewuserImage)
        val upvoteButton: ImageView = itemView.findViewById(R.id.reviewupvoteButton)
        val downvoteButton: ImageView = itemView.findViewById(R.id.reviewdownvoteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review,parent,false))
        viewHolder.upvoteButton.setOnClickListener{
            listener.onVoteClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)   //If like clicked listner will know by that
        }
        viewHolder.downvoteButton.setOnClickListener{
            listener.onVotedownClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)   //If like clicked listner will know by that
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: review) {
        holder.reviewText.text = model.text
        holder.userText.text = model.createdBy.displayName
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.votedBy.size.toString()
        holder.createdAt.text = TimeAgo.using(model.createdAt)

    }

    interface IReviewAdapter{
        fun onVoteClicked(postId:String)
        fun onVotedownClicked(postId: String)
    }


}
