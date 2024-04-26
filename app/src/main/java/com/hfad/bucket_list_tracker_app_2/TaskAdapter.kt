package com.hfad.bucket_list_tracker_app_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.bucket_list_tracker_app_2.databinding.TaskLayoutBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // on below line we are creating a view holder class.
    class TaskViewHolder(val itemBinding: TaskLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    //DiffUtil.ItemCallback is used to determine the difference between two list.
    private val differCallback = object  : DiffUtil.ItemCallback<Task>(){

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.taskContent == newItem.taskContent &&
                    oldItem.taskTitle == newItem.taskTitle
        }

        //is called to check if two items are the same in the Task data class.
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
    //AsyncListDiffer is used to determine the differences between two list which helps in smooth UI update.
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // inflating our layout file for each item of recycler view.
       return TaskViewHolder(
           TaskLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
      val currentTask = differ.currentList[position]

        holder.itemBinding.noteTitle.text = currentTask.taskTitle
        holder.itemBinding.noteDesc.text = currentTask.taskContent

        // on below line we are adding click listener
        // to our recycler view item.
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }
    }
}