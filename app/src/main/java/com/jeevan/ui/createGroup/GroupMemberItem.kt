package com.jeevan.ui.createGroup

import android.view.View
import com.jeevan.R
import com.jeevan.databinding.ItemAddGroupMemberBinding
import com.jeevan.fragment.User
import com.xwray.groupie.viewbinding.BindableItem

class GroupMemberItem(
    private val workspaceUser: User,
    private val isSelected: Boolean,
    private val onClick: (User) -> (Unit)
) : BindableItem<ItemAddGroupMemberBinding>() {
    override fun bind(viewBinding: ItemAddGroupMemberBinding, position: Int) {
        viewBinding.tvMemberName.text = workspaceUser.name
        viewBinding.imMemberDp.visibility = View.VISIBLE
        viewBinding.imMemberAdd.setImageResource(if(isSelected) R.drawable.ic_check else R.drawable.ic_plus)
        if(!isSelected) {
            viewBinding.imMemberAdd.setOnClickListener {
                onClick(workspaceUser)
                viewBinding.imMemberAdd.setImageResource(R.drawable.ic_check)
                it.setOnClickListener(null)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_add_group_member
    }

    override fun initializeViewBinding(view: View): ItemAddGroupMemberBinding {
        return ItemAddGroupMemberBinding.bind(view)
    }
}