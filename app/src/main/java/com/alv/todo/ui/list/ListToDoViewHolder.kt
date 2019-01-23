package com.alv.todo.ui.list

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.alv.todo.R
import com.alv.todo.databinding.ListToDoItemBinding
import kotlinx.android.synthetic.main.list_to_do_item.view.*


class ListToDoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ListToDoItemBinding? = DataBindingUtil.bind(view)
    val todoCheckBox: CheckBox = view.findViewById(R.id.todo_item)
    val todoDate: TextView = view.findViewById(R.id.date_view)
}