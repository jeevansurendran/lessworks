package com.jeevan.ui.group

import android.view.View
import com.jeevan.R
import com.jeevan.databinding.ItemTaskBinding
import com.jeevan.fragment.Task
import com.jeevan.utils.Formatter
import com.xwray.groupie.viewbinding.BindableItem
import java.util.*

class GroupTaskItem(private val pair: Pair<Task, Boolean>) : BindableItem<ItemTaskBinding>() {
    override fun bind(viewBinding: ItemTaskBinding, position: Int) {
        /**
         * I know it can be done in a single line but these days
         * i have become a huge fan of KISS
         */
        val (task, isMe) = pair
        if (isMe) {
            // its the user
            if (task.status) {
                // done
                viewBinding.tvTaskWho.text = "Done by you"
            } else {
                // not done
                viewBinding.tvTaskWho.text = "You added"
            }
        } else {
            // its someone else
            if (task.status) {
                // done
                viewBinding.tvTaskWho.text = "Done by ${task.user.name}"
            } else {
                // not done
                viewBinding.tvTaskWho.text = "${task.user.name} added"
            }
        }
        viewBinding.cbTaskDone.isChecked = task.status
        viewBinding.tvTaskDetails.text = task.text

        viewBinding.tvTaskTime.text = Formatter.formatTimeISO(task.created_at as String)
//
        viewBinding.tvTaskDate.visibility = View.GONE
        viewBinding.imTaskTime.visibility = View.GONE
        task.deadline?.let {
            viewBinding.imTaskTime.visibility = View.VISIBLE
            viewBinding.tvTaskDate.visibility = View.VISIBLE
            viewBinding.tvTaskDate.text = Formatter.formatDurationISO(it as String)
        }

    }

    override fun getLayout(): Int {
        return R.layout.item_task
    }

    override fun initializeViewBinding(view: View): ItemTaskBinding {
        return ItemTaskBinding.bind(view)
    }
}