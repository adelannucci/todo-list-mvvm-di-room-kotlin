package com.alv.todo.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alv.todo.R
import com.alv.todo.domain.ToDo
import com.alv.todo.ui.Constants.DELETE_TODO
import com.alv.todo.ui.Constants.UPDATE_TODO
import com.alv.todo.ui.RxBus
import java.text.SimpleDateFormat
import java.util.*

class ListToDoAdapter(
        var todo: List<ToDo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListToDoViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_to_do_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ListToDoViewHolder).binding
        binding?.toDo = todo[position]
        binding?.executePendingBindings()

        holder.todoCheckBox.isChecked = todo[position].isComplete
        holder.todoDate.text = formatDate(todo[position])

        holder.todoCheckBox.setOnCheckedChangeListener { view, isChecked ->
            todo[position].isComplete = isChecked
            holder.todoDate.text = formatDate(todo[position])
            RxBus.publish(UPDATE_TODO, todo[position])
        }

        holder.todoCheckBox.setOnLongClickListener {
            val item = todo[position]
            RxBus.publish(DELETE_TODO, item)
            return@setOnLongClickListener true
        }
    }

    private fun formatDate(toDo: ToDo): String {
        val date: Date
        val formatDate: String
        val sdf = SimpleDateFormat("dd/MM/yyy")
        if(toDo.isComplete) {
            date = Date(toDo.dateCompleted)
            formatDate = sdf.format(date)
        } else {
            date = Date(toDo.dateCreated)
            formatDate = sdf.format(date)
        }
        return formatDate
    }

    override fun getItemCount() = todo.size

}