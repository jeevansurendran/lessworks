package com.jeevan.ui.createWorkspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jeevan.R
import com.jeevan.databinding.FragmentMainCreateGroupBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2

class MainCreateGroupFragment : BottomSheetDialogFragment2(R.layout.fragment_main_create_group) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainCreateGroupBinding.bind(view)
    }
}