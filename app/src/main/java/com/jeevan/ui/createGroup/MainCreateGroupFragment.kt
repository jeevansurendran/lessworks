package com.jeevan.ui.createGroup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jeevan.R
import com.jeevan.databinding.FragmentMainCreateGroupBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2

class MainCreateGroupFragment : BottomSheetDialogFragment2(R.layout.fragment_main_create_group) {

    private val args by navArgs<MainCreateGroupFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainCreateGroupBinding.bind(view)
        setupListeners(binding)
    }

    private fun setupListeners(binding: FragmentMainCreateGroupBinding) {
        binding.ibCreateGroupClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ibCreateGroupNext.setOnClickListener {
            val text =  binding.tilCreateGroupName.editText?.text
            if(text.isNullOrBlank()) {
                binding.tilCreateGroupName.error = "Group name cannot be empty"
                return@setOnClickListener
            }
            val action = MainCreateGroupFragmentDirections.addMembers(args.workspaceId, text.toString())
            findNavController().navigate(action)
        }
    }
}