package com.jeevan.ui.main

import android.view.View
import com.jeevan.R
import com.jeevan.databinding.ItemWorkspaceGroupBinding
import com.jeevan.fragment.Group
import com.xwray.groupie.viewbinding.BindableItem

class GroupItem(private val group: Group, private val onClick:(View) -> (Unit)) : BindableItem<ItemWorkspaceGroupBinding>() {
    override fun bind(viewBinding: ItemWorkspaceGroupBinding, position: Int) {
        viewBinding.tvRoomName.text = group.name
        viewBinding.root.setOnClickListener(onClick)
    }

    override fun getLayout(): Int {
        return R.layout.item_workspace_group
    }

    override fun initializeViewBinding(view: View): ItemWorkspaceGroupBinding {
        return ItemWorkspaceGroupBinding.bind(view)
    }
}