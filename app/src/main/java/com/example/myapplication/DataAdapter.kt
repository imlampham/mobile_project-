package com.example.myapplication

import SheetEntry
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(private val dataList: List<SheetEntry>) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val entry = dataList[position]
        holder.textView.text = "Entry ${position + 1}: cot 1 is ${entry.column1}, cot 2 is ${entry.column2}"
    }

    override fun getItemCount(): Int = dataList.size
}
