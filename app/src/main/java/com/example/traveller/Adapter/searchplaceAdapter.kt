package com.example.traveller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traveller.Model.searchplace
import com.example.traveller.R

class searchplaceAdapter(courseModelArrayList: ArrayList<searchplace>, private val listener:onitemClicked) : RecyclerView.Adapter<searchplaceAdapter.ViewHolder>() {

    // creating a variable for array list and context.
    private var courseModelArrayList: ArrayList<searchplace>

    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<searchplace>) {
        // below line is to add our filtered
        // list in our course array list.
        courseModelArrayList = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // below line is to inflate our layout.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.searchplace_rv_item, parent, false)

        val viewholder = ViewHolder(view)
        view.setOnClickListener{
            listener.onitemClicked(courseModelArrayList[viewholder.adapterPosition])
        }

        return viewholder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our views of recycler view.
        val model: searchplace = courseModelArrayList[position]
        holder.courseNameTV.setText(model.searchplacename)
//        holder.courseDescTV.setText(model.getCourseDescription())
    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return courseModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        var courseNameTV: TextView
//        var courseDescTV: TextView

        init {
            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName)
//            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription)
        }
    }

    // creating a constructor for our variables.
    init {
        this.courseModelArrayList = courseModelArrayList
    }
}
interface onitemClicked{
    fun onitemClicked(item:searchplace)
}