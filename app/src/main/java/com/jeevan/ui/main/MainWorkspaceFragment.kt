package com.jeevan.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jeevan.R
import com.jeevan.databinding.FragmentMainWorkspaceBinding
import com.jeevan.queries.GetWorkspacesQuery
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

    private fun setWorkspaceSelector(
        workspaceList: List<GetWorkspacesQuery.Workspace>,
        workspacesAdapter: GroupieAdapter
    ) {
        val selectedWorkspaceId = workspacesViewModel.workspaceId.value
        workspacesAdapter.replaceAll(workspaceList.map {
            WorkspaceItem(selectedWorkspaceId == it.id, it.id as String) {
                workspacesViewModel.setWorkspaceId(it)
                setWorkspaceSelector(workspaceList, workspacesAdapter)
            }
        })
        workspacesAdapter.add(AddWorkspaceItem {

        })
    }

    private fun setupObservers(binding: FragmentMainWorkspaceBinding) {
        workspacesViewModel.workspaces.observe(viewLifecycleOwner) {
            it?.let { result ->
                if (result.isSuccess) {
                    val workspacesAdapter =
                        binding.rvWorkspaces.adapter as GroupieAdapter // not so safe but is ok
                    result.getOrNull()?.let {
                        setWorkspaceSelector(it, workspacesAdapter)
                    }
                }
            }
        }
        workspacesViewModel.selectedWorkspace.observe(viewLifecycleOwner) {
            it?.let { result ->
                if(result.isSuccess) {
                    val groupAdapter = binding.rvWorkspaceGroup.adapter as GroupieAdapter
                    val directAdapter = binding.rvWorkspaceDirect.adapter as GroupieAdapter
                    setLoading(binding, false)
                    result.getOrNull()?.let {
                        binding.tvWorkspaceName.text = it.name
                        groupAdapter.replaceAll(it.groups.map { group ->
                            ConnectItem(group.fragments.group.name, "") {
                                openGroup(group.fragments.group.id as String)
                            }
                        })
                        directAdapter.replaceAll(it.workspace_users.map { direct->
                            ConnectItem(direct.user.fragments.user.name, "") {

                            }
                        })
                    }
                }
            }
        }
    }

    private fun setupListeners(binding: FragmentMainWorkspaceBinding) {
        binding.rvWorkspaces.adapter = GroupieAdapter()
        binding.rvWorkspaceGroup.adapter = GroupieAdapter()
        binding.rvWorkspaceDirect.adapter = GroupieAdapter()

        binding.imWorkspaceGroupAdd.setOnClickListener {
            workspacesViewModel.selectedWorkspace.value?.getOrNull()?.let {
                val directions = MainWorkspaceFragmentDirections.createGroup(it.id.toString())
                findNavController().navigate(directions)
            }

        }
        setLoading(binding)

    }

    private fun setLoading(binding: FragmentMainWorkspaceBinding, isLoading: Boolean = true) {
        if (isLoading) {
            binding.clWorkspaceContainer.visibility = View.INVISIBLE
            binding.cpiLoading.visibility = View.VISIBLE
        } else {
            binding.clWorkspaceContainer.visibility = View.VISIBLE
            binding.cpiLoading.visibility = View.GONE
        }
    }

    private fun openGroup(groupId: String) {
        val action = MainWorkspaceFragmentDirections.openGroup(groupId)
        findNavController().navigate(action)
    }

}