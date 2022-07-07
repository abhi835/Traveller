package com.example.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traveller.Model.States
import com.example.traveller.R

class StatesAdapter(private val stateList: ArrayList<States>) : RecyclerView.Adapter<StatesAdapter.ViewHolder>() {



    private lateinit var mlistner:onItemClickedlistner

    interface onItemClickedlistner{
        fun onitemClicked(position: Int)
    }

    fun setOnClickedListner(listener:onItemClickedlistner){
        mlistner = listener
    }



    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gridview_item, parent, false)

        return ViewHolder(view,mlistner)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = stateList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel.statePic)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.stateName

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return stateList.size

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: onItemClickedlistner) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.StateImage)
        val textView: TextView = itemView.findViewById(R.id.StateTitle)

        init {
            ItemView.setOnClickListener{
                listener.onitemClicked(adapterPosition)
            }
        }
    }
}