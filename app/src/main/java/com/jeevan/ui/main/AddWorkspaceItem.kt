package com.jeevan.ui.main

import android.view.View
import com.jeevan.R
import com.jeevan.databinding.ItemWorkspaceAddBinding
import com.xwray.groupie.viewbinding.BindableItem

class AddWorkspaceItem(
    private val onClick: () -> Unit,
) :
    BindableItem<ItemWorkspaceAddBinding>() {
    override fun bind(viewBinding: ItemWorkspaceAddBinding, position: Int) {
        viewBinding.root.setOnClickListener {
            onClick()
        }
    }

    override fun getLayout(): Int = R.layout.item_workspace_add

    override fun initializeViewBinding(view: View): ItemWorkspaceAddBinding {
        return ItemWorkspaceAddBinding.bind(view)
    }
}