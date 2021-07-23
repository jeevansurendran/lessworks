package com.jeevan.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jeevan.R
import com.jeevan.databinding.FragmentMainWorkspaceBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainWorkspaceFragment: Fragment(R.layout.fragment_main_workspace) {
    private val workspacesViewModel by activityViewModels<WorkspacesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainWorkspaceBinding.bind(view)
        // listeners above observers
        setupListeners(binding)
        setupObservers(binding)
    }


    private fun setupObservers(binding: FragmentMainWorkspaceBinding) {
        workspacesViewModel.workspaces.observe(viewLifecycleOwner) {
            it?.let { result ->
                if(result.isSuccess) {
                    val adapter = binding.rvWorkspaces.adapter as GroupieAdapter // not so safe but is ok
                    result.getOrNull()?.let {
                        adapter.replaceAll(it.map {
                            WorkspaceItem()
                        })
                    }
                }
            }
        }
        workspacesViewModel.selectedWorkspace.observe(viewLifecycleOwner) {
            it?.let { result ->
                if(result.isSuccess) {
                    setLoading(binding, false)
                    result.getOrNull()?.let {
                        binding.tvWorkspaceName.text = it.name
                    }
                }
            }
        }
    }

    private fun setupListeners(binding: FragmentMainWorkspaceBinding) {
        binding.rvWorkspaces.adapter = GroupieAdapter()
        binding.rvWorkspaceGroup.adapter = GroupieAdapter()

        binding.imWorkspaceGroupAdd.setOnClickListener {
            workspacesViewModel.selectedWorkspace.value?.getOrNull()?.let {
                val directions = MainWorkspaceFragmentDirections.createGroup(it.id.toString())
                findNavController().navigate(directions)
            }

        }
        setLoading(binding)

    }

    private fun setLoading(binding: FragmentMainWorkspaceBinding, isLoading: Boolean = true) {
        if(isLoading) {
            binding.clWorkspaceContainer.visibility = View.INVISIBLE
            binding.cpiLoading.visibility = View.VISIBLE
        } else {
            binding.clWorkspaceContainer.visibility = View.VISIBLE
            binding.cpiLoading.visibility = View.GONE
        }
    }

}