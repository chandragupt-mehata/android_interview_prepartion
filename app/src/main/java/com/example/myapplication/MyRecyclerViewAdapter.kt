package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(private val mList: List<ItemsViewModel>):
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: onViewAttachedToWindow ${holder.hashCode()}")
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: onViewDetachedFromWindow ${holder.hashCode()}")
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: onViewRecycled ${holder.hashCode()}")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: onAttachedToRecyclerView")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: onDetachedFromRecyclerView")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mList[position].text
        Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: onBindViewHolder $position")
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        init {
            Log.d("MyRecyclerViewAdapter", "MyRecyclerViewAdapter: ${itemView.hashCode()}")
        }
    }
}