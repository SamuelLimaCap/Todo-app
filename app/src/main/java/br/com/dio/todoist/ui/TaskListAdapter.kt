package br.com.dio.todoist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.todoist.R
import br.com.dio.todoist.databinding.ItemTaskBinding
import br.com.dio.todoist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDate.text = "${item.date} ${item.hour}"

            binding.ivMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

}