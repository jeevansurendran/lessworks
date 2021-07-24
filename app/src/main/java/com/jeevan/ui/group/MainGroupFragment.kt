package com.jeevan.ui.group

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jeevan.R
import com.jeevan.databinding.FragmentMainGroupBinding
import com.jeevan.fragment.Task
import com.jeevan.ui.main.WorkspacesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainGroupFragment: Fragment(R.layout.fragment_main_group) {

    private val args: MainGroupFragmentArgs by navArgs()
    private val groupViewModel by hiltNavGraphViewModels<GroupViewModel>(R.id.mainGroupFragment)
    private val mainViewModel by activityViewModels<WorkspacesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainGroupBinding.bind(view)
        setupObservers(binding)
        setupViews(binding)
    }

    private fun setupViews(binding: FragmentMainGroupBinding) {
        binding.ibGroupCreateTask.setOnClickListener {
            val action = MainGroupFragmentDirections.addTask()
            findNavController().navigate(action)
        }
    }

    private fun setupObservers(binding: FragmentMainGroupBinding) {
        groupViewModel.group.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    binding.tvGroupName.text = it.fragments.group.name
                    binding.tvMemberCount.text = "${it.fragments.group.group_users.size} Members"
                    setupTasks(binding, it.group_tasks.map { it.task.fragments.task })

                }
            }
        }
        mainViewModel.selectedWorkspace.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isSuccess) {
                    it.getOrNull()?.let {
                        it.groups.find { it.fragments.group.id == args.groupId }?.fragments?.group?.let {
                            binding.tvGroupName.text = it.name
                            binding.tvMemberCount.text = "${it.group_users.size} Members"
                        }
                    }
                }
            }
        }
    }

    private fun setupTasks(binding: FragmentMainGroupBinding, list: List<Task>) {
        if (list.isEmpty()) {
            binding.hsvGroupFilter.visibility = View.GONE
            binding.nsvGroupTasks.visibility = View.GONE
            binding.cvGroupAddTask.visibility = View.VISIBLE
            return
        }
        binding.hsvGroupFilter.visibility = View.GONE
        binding.nsvGroupTasks.visibility = View.GONE
        binding.cvGroupAddTask.visibility = View.VISIBLE
    }
}