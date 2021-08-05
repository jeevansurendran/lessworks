package com.jeevan.ui.group

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.jeevan.R
import com.jeevan.databinding.FragmentMainGroupBinding
import com.jeevan.fragment.Task
import com.jeevan.ui.main.WorkspacesViewModel
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainGroupFragment : Fragment(R.layout.fragment_main_group) {

    private val args by navArgs<MainGroupFragmentArgs>()
    private val groupViewModel by hiltNavGraphViewModels<GroupViewModel>(R.id.mainGroupFragment)
    private val mainViewModel by activityViewModels<WorkspacesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainGroupBinding.bind(view)
        setupViews(binding)
        setupObservers(binding)
    }

    private fun setupViews(binding: FragmentMainGroupBinding) {
        binding.ibGroupCreateTask.setOnClickListener {
            val action = MainGroupFragmentDirections.addTask(args.groupId)
            findNavController().navigate(action)
        }
        val itemDecor = DividerItemDecoration(context, HORIZONTAL)
        binding.rvGroupTask.adapter = GroupieAdapter()
        binding.rvGroupTask.addItemDecoration(itemDecor)
        binding.cpiGroupLoading.visibility = View.VISIBLE
        binding.tvGroupBack.setOnClickListener {
            findNavController().popBackStack()
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
                binding.cpiGroupLoading.visibility = View.GONE
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
            binding.nsvGroupTasks.visibility = View.GONE
            binding.cvGroupAddTask.visibility = View.VISIBLE
            return
        }
        binding.nsvGroupTasks.visibility = View.VISIBLE
        binding.cvGroupAddTask.visibility = View.GONE
        val adapter = binding.rvGroupTask.adapter as GroupieAdapter
        lifecycleScope.launch {
            val user = groupViewModel.user.first()
            user.getOrNull()?.let {
                val uid = it.getUid()
                adapter.replaceAll(list.map {task ->
                    GroupTaskItem(task to (task.user.uid == uid)) {
                        groupViewModel.updateGroupTask(task.id as String, it)
                    }
                })
            }
        }
    }

}