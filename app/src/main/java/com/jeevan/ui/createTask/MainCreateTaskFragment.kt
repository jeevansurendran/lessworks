package com.jeevan.ui.createTask

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.jeevan.R
import com.jeevan.databinding.FragmentMainCreateTaskBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2
import com.jeevan.utils.Formatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainCreateTaskFragment : BottomSheetDialogFragment2(R.layout.fragment_main_create_task) {

    private val viewModel by hiltNavGraphViewModels<CreateTaskViewModel>(
        R.id.mainCreateTaskFragment
    )
    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set a deadline for your task")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainCreateTaskBinding.bind(view)
        setupViews(binding)
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
    }
}