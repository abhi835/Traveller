package com.example.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.traveller.Model.userphotos
import com.example.traveller.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.github.marlonlom.utilities.timeago.TimeAgo

class userphotosAdapter(options: FirestoreRecyclerOptions<userphotos>, val listener: userphotosAdapter.IUserPhotosAdapter) :
    FirestoreRecyclerAdapter<userphotos, userphotosAdapter.PostViewHolder>(
        options
    ) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val userPhotosslider:ImageView  =itemView.findViewById(R.id.userphoto)
val userPhotosslider:ImageSlider =itemView.findViewById(R.id.userphoto)
        val userText: TextView = itemView.findViewById(R.id.userphotoName)
        val createdAt: TextView = itemView.findViewById(R.id.photocreatedAt)
        val likeCount: TextView = itemView.findViewById(R.id.likephotoCount)
        val userImage: ImageView = itemView.findViewById(R.id.userphotoImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likephotoButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder = userphotosAdapter.PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_userphotos, parent, false)
        )
       return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: userphotos) {

        val list:List<String> = model.userPhotos
        val simplearray = ArrayList<SlideModel>()
        for(i in list){
            simplearray.add(SlideModel(i))
        }
        holder.userPhotosslider.setImageList(simplearray)

//        Glide.with(holder.userPhotosslider .context).load(model.userPhotos).into(holder.userPhotosslider)
        holder.userText.text = model.createdBy.displayName
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.votedBy.size.toString()
        holder.createdAt.text = TimeAgo.using(model.createdAt)

    }

interface IUserPhotosAdapter{

}
}
