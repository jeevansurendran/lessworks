package com.jeevan.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.color.MaterialColors
import com.jeevan.R


class IconButton : AppCompatImageButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val primaryColor = MaterialColors.getColor(this, R.attr.colorPrimary)
        val colorOnPrimary = MaterialColors.getColor(this, R.attr.colorOnPrimary)
        imageTintList = ColorStateList.valueOf(colorOnPrimary)
        backgroundTintList = ColorStateList.valueOf(primaryColor)
    }

}