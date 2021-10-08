package com.jeevan.ui.createWorkspace

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jeevan.R
import com.jeevan.databinding.FragmentMainCreateWorkspaceBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2
import com.jeevan.ui.main.WorkspacesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCreateWorkspaceFragment :
    BottomSheetDialogFragment2(R.layout.fragment_main_create_workspace) {

    private val viewModel: CreateWorkspaceViewModel by viewModels()
    private val activityViewModel: WorkspacesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainCreateWorkspaceBinding.bind(view)
        setupViews(binding)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentMainCreateWorkspaceBinding) {
        viewModel.workspaceId.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    activityViewModel.getWorkspaces()
                    activityViewModel.setWorkspaceId(it)
                    dismiss()
                }
            } else {
                it.exceptionOrNull()?.let {
                    binding.tilCreateWorkspaceName.error = it.message
                }
            }
        }

    }

    private fun setupViews(binding: FragmentMainCreateWorkspaceBinding) {
        binding.ibCreateWorkspaceNext.setOnClickListener {
            createNewWorkspace(binding.tilCreateWorkspaceName.editText?.text.toString(), binding)
        }
        binding.ibCreateWorkspaceClose.setOnClickListener {
            dismiss()
        }
    }

    private fun createNewWorkspace(text: String, binding: FragmentMainCreateWorkspaceBinding) {
        if (text.isBlank()) {
            binding.tilCreateWorkspaceName.error = "Workspace cannot be blank"
            return
        }
        viewModel.createNewWorkspace(text)
    }
}