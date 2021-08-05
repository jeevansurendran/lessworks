package com.jeevan.ui.direct

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.jeevan.R
import com.jeevan.databinding.FragmentMainDirectBinding
import com.jeevan.fragment.Task
import com.jeevan.ui.group.GroupTaskItem
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainDirectFragment : Fragment(R.layout.fragment_main_direct) {
    private val viewModel by hiltNavGraphViewModels<DirectViewModel>(
        R.id.mainDirectFragment
    )
    private val navArgs : MainDirectFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainDirectBinding.bind(view)
        setupViews(binding)
        setupObservers(binding)
    }

    private fun setupViews(binding: FragmentMainDirectBinding) {
        binding.ibDirectCreateTask.setOnClickListener {
            viewModel.direct.value?.getOrNull()?.id?.let {
                val action = MainDirectFragmentDirections.createDirectTask(it as String)
                findNavController().navigate(action)
            }

        }
        val itemDecor = DividerItemDecoration(context, ClipDrawable.HORIZONTAL)
        binding.rvDirectTask.adapter = GroupieAdapter()
        binding.rvDirectTask.addItemDecoration(itemDecor)
        binding.cpiDirectLoading.visibility = View.VISIBLE
        binding.tvDirectName.text = navArgs.userName
        binding.tvDirectBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers(binding: FragmentMainDirectBinding) {
        viewModel.direct.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    setupTasks(binding, it.direct_tasks.map { it.task.fragments.task })
                }
                binding.cpiDirectLoading.visibility = View.GONE
            }
        }
    }

    private fun setupTasks(binding: FragmentMainDirectBinding, list: List<Task>) {
        if (list.isEmpty()) {
            binding.nsvDirectTasks.visibility = View.GONE
            binding.cvDirectAddTask.visibility = View.VISIBLE
            return
        }
        binding.nsvDirectTasks.visibility = View.VISIBLE
        binding.cvDirectAddTask.visibility = View.GONE
        val adapter = binding.rvDirectTask.adapter as GroupieAdapter
        lifecycleScope.launch {
            val user = viewModel.user.first()
            user.getOrNull()?.let {
                val uid = it.getUid()
                adapter.replaceAll(list.map { task ->
                    GroupTaskItem(task to (task.user.uid == uid)) {
                        viewModel.updateDirectTask(task.id as String, it)
                    }
                })
            }
        }
    }
}