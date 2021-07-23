package com.jeevan.ui.createGroup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jeevan.R
import com.jeevan.databinding.FragmentMainAddGroupMemberBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2
import com.jeevan.ui.main.WorkspacesViewModel
import com.jeevan.utils.Constants.GROUP_MEMBER_SEARCH_LENGTH
import com.jeevan.utils.debounce
import com.jeevan.utils.textChannel
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach

@AndroidEntryPoint
class AddGroupMembersFragment :
    BottomSheetDialogFragment2(R.layout.fragment_main_add_group_member) {

    val args by navArgs<AddGroupMembersFragmentArgs>()
    private val mainViewModel by activityViewModels<WorkspacesViewModel>()
    private val addGroupMembersViewModel by viewModels<AddGroupMembersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainAddGroupMemberBinding.bind(view)
        setupListeners(binding)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentMainAddGroupMemberBinding) {
        mainViewModel.selectedWorkspace.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isSuccess) {
                    if (binding.tilAddGroupMemberSearch.editText?.text?.length ?: 0 < GROUP_MEMBER_SEARCH_LENGTH)
                        it.getOrNull()?.workspace_users
                            ?.map {
                                GroupMemberItem(
                                    it.user.fragments.user,
                                    addGroupMembersViewModel.usersList.contains(it.user.fragments.user.uid)
                                ) {
                                    addGroupMembersViewModel.usersList.add(it.uid)
                                    setMembersCount(binding)
                                }
                            }?.let {
                                val adapter =
                                    binding.rvAddGroupMemberNames.adapter as GroupieAdapter
                                adapter.replaceAll(it)
                            }
                }
            }
        }
        addGroupMembersViewModel.searchUsersResult.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isSuccess) {
                    if (binding.tilAddGroupMemberSearch.editText?.text?.length ?: 0 >= GROUP_MEMBER_SEARCH_LENGTH)
                        it.getOrNull()
                            ?.map {
                                GroupMemberItem(
                                    it.fragments.user,
                                    addGroupMembersViewModel.usersList.contains(it.fragments.user.uid)
                                ) {
                                    addGroupMembersViewModel.usersList.add(it.uid)
                                    setMembersCount(binding)
                                }
                            }?.let {
                                val adapter =
                                    binding.rvAddGroupMemberNames.adapter as GroupieAdapter
                                adapter.replaceAll(it)
                            }
                }
            }
        }
        addGroupMembersViewModel.addGroupResult.observe(viewLifecycleOwner) {
            it?.let {
                if(it.isSuccess) {
                    it.getOrNull()?.also {
                        dismiss()
                        binding.ibAddGroupMemberNext.isEnabled = true
                    }
                }
            }
        }
    }

    private fun setupListeners(binding: FragmentMainAddGroupMemberBinding) {
        binding.rvAddGroupMemberNames.adapter = GroupieAdapter()
        lifecycleScope.launchWhenStarted {
            binding.tilAddGroupMemberSearch.editText
                ?.textChannel()
                ?.debounce(100)
                ?.consumeEach {
                    addGroupMembersViewModel.search(it)
                }
        }
        setMembersCount(binding)
        binding.ibAddGroupMemberNext.setOnClickListener {
            binding.ibAddGroupMemberNext.isEnabled = false
            addGroupMembersViewModel.addGroup(args.workspaceId, args.groupName)

        }
    }

    private fun setMembersCount(binding: FragmentMainAddGroupMemberBinding) {
        binding.tvAddGroupMemberCount.text = "(${addGroupMembersViewModel.usersList.size} Members)"
    }

}