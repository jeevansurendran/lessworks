package com.jeevan.ui.main

import android.view.View
import com.jeevan.R
import com.jeevan.databinding.ItemWorkspaceLogoBinding
import com.xwray.groupie.viewbinding.BindableItem

class WorkspaceItem : BindableItem<ItemWorkspaceLogoBinding>() {
    override fun bind(viewBinding: ItemWorkspaceLogoBinding, position: Int) {
        // a photo setup
    }

    override fun getLayout(): Int = R.layout.item_workspace_logo

    override fun initializeViewBinding(view: View): ItemWorkspaceLogoBinding {
        return ItemWorkspaceLogoBinding.bind(view)
    }
}