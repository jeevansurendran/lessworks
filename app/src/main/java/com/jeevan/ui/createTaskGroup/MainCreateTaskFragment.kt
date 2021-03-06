package com.jeevan.ui.createTaskGroup

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.jeevan.R
import com.jeevan.databinding.FragmentMainCreateTaskBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2
import com.jeevan.ui.group.GroupViewModel
import com.jeevan.utils.DateTimeUtils
import com.jeevan.utils.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainCreateTaskFragment : BottomSheetDialogFragment2(R.layout.fragment_main_create_task) {

    private val viewModel by hiltNavGraphViewModels<CreateTaskViewModel>(
        R.id.mainCreateTaskFragment
    )
    private val groupViewModel by hiltNavGraphViewModels<GroupViewModel>(R.id.mainGroupFragment)
    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set a deadline for your task")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

    private val timePicker =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Pick a time")
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
                    groupViewModel.getGroupData()
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
            datePicker.show(childFragmentManager, "date-picker")
        }
        binding.tvCreateTaskTime.setOnClickListener(onClickListener)
        binding.imCreateTaskTime.setOnClickListener(onClickListener)

        datePicker.addOnPositiveButtonClickListener {
            viewModel.deadlineDate = Date(it)
            binding.tvCreateTaskTime.text = Formatter.formatDuration(viewModel.deadlineDate!!)
            timePicker.show(childFragmentManager, "time-picker")
        }
        timePicker.addOnPositiveButtonClickListener {
            viewModel.deadlineDate = DateTimeUtils.setDateTime(
                viewModel.deadlineDate ?: Calendar.getInstance().time,
                timePicker.hour,
                timePicker.minute
            )
            binding.tvCreateTaskTime.text = viewModel.deadlineDate?.let { it1 ->
                Formatter.formatDuration(
                    it1
                )
            }
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