package com.example.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traveller.Model.StateCities
import com.example.traveller.R

class StateCitiesAdapter(private val citiesArray: ArrayList<String>,private val listener:IstateCitiesAdapter):RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_statecities,parent,false)

        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onStateCitiesClicked(citiesArray[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentitem = citiesArray[position]
        holder.statecities.text = currentitem
    }

    override fun getItemCount(): Int {
       return citiesArray.size
    }


}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val statecities: TextView = itemView. findViewById(R.id.stateCities)
}
interface IstateCitiesAdapter{
    fun onStateCitiesClicked(item:String)
}