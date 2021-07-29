package com.jeevan.ui.main

import android.view.View
import androidx.core.content.ContextCompat
import com.jeevan.R
import com.jeevan.databinding.ItemWorkspaceGroupBinding
import com.xwray.groupie.viewbinding.BindableItem

class ConnectItem(
    private val name: String,
    imageURL: String?,
    private val type: Boolean,
    private val onClick: (View) -> (Unit)
) : BindableItem<ItemWorkspaceGroupBinding>() {
    override fun bind(viewBinding: ItemWorkspaceGroupBinding, position: Int) {
        viewBinding.tvRoomName.text = name
        viewBinding.root.setOnClickListener(onClick)
        if (!type) {
            viewBinding.imRoomLogo.setBackgroundColor(
                ContextCompat.getColor(
                    viewBinding.root.context,
                    R.color.red_500
                )
            )
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_workspace_group
    }

    override fun initializeViewBinding(view: View): ItemWorkspaceGroupBinding {
        return ItemWorkspaceGroupBinding.bind(view)
    }
}