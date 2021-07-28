package com.jeevan.ui.main

import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.jeevan.R
import com.jeevan.databinding.ItemWorkspaceLogoBinding
import com.xwray.groupie.viewbinding.BindableItem

class WorkspaceItem(
    private val isSelected: Boolean,
    private val workspaceId: String,
    private val onClick: (String) -> Unit,
) :
    BindableItem<ItemWorkspaceLogoBinding>() {
    override fun bind(viewBinding: ItemWorkspaceLogoBinding, position: Int) {
        if (!isSelected) {
            viewBinding.root.setOnClickListener {
                onClick(workspaceId)
            }
            val typedValue = TypedValue()
            viewBinding.root.context.theme.resolveAttribute(R.attr.colorSurface, typedValue, true)
            viewBinding.root.strokeColor =
                ContextCompat.getColor(viewBinding.root.context, typedValue.resourceId)

        } else {
            viewBinding.root.strokeColor =
                ContextCompat.getColor(viewBinding.root.context, R.color.gray_200)
        }
    }

    override fun getLayout(): Int = R.layout.item_workspace_logo

    override fun initializeViewBinding(view: View): ItemWorkspaceLogoBinding {
        return ItemWorkspaceLogoBinding.bind(view)
    }
}