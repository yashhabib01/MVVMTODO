package com.example.mvvmtodo.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtodo.data.Task
import com.example.mvvmtodo.databinding.ItemTaskBinding


class TaskAdapter(private val listener:onItemClickListener) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       val currentItem = getItem(position)

        holder.bind(currentItem)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                     val task = getItem(position)
                      listener.onItemClick(task)
                    }
                }

                checkboxCompleted.setOnClickListener{
                    val position  = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val task = getItem(position)
                        listener.onCheckBoxClick(task,checkboxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task){
            binding.apply {
                checkboxCompleted.isChecked = task.completed
                textViewName.text = task.name
                textViewName.paint.isStrikeThruText =task.completed
                labelPriority.isVisible = task.important
            }
        }
    }

    interface onItemClickListener{
        fun onItemClick(task:Task)
        fun onCheckBoxClick(task:Task,isChecked:Boolean)
    }


    class DiffCallBack : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id ==  newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =  oldItem == newItem


    }

}