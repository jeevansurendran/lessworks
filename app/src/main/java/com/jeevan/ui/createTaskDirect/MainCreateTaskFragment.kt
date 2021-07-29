package com.jeevan.ui.createTaskDirect

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.jeevan.R
import com.jeevan.databinding.FragmentMainCreateTaskBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2
import com.jeevan.ui.direct.DirectViewModel
import com.jeevan.ui.group.GroupViewModel
import com.jeevan.utils.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainCreateTaskFragment : BottomSheetDialogFragment2(R.layout.fragment_main_create_task) {

    private val viewModel by hiltNavGraphViewModels<CreateTaskViewModel>(
        R.id.mainCreateTaskFragment2
    )
    private val directViewModel by hiltNavGraphViewModels<DirectViewModel>(R.id.mainDirectFragment)
    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set a deadline for your task")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainCreateTaskBinding.bind(view)
        setupViews(binding)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentMainCreateTaskBinding) {
        viewModel.task.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    directViewModel.getDirect()
                    dismiss()
                }
            } else {
                it.exceptionOrNull()?.let {
                    binding.tilCreateTaskName.error = it.message
                }
            }
        }

    }

    private fun setupViews(binding: FragmentMainCreateTaskBinding) {
        val onClickListener: (View) -> (Unit) = {
            datePicker.show(childFragmentManager, "tag")
        }
        binding.tvCreateTaskTime.setOnClickListener(onClickListener)
        binding.imCreateTaskTime.setOnClickListener(onClickListener)

        datePicker.addOnPositiveButtonClickListener {
            viewModel.deadlineDate = Date(it)
            binding.tvCreateTaskTime.text = Formatter.formatDuration(Date(it))
        }
        binding.ibCreateTaskNext.setOnClickListener {
            createTask(binding.tilCreateTaskName.editText?.text.toString(), binding)
        }
    }

    private fun createTask(text: String, binding: FragmentMainCreateTaskBinding) {
        if (text.isBlank()) {
            binding.tilCreateTaskName.error = "Task cannot be blank"
            return
        }
        viewModel.createTask(text)
    }
}