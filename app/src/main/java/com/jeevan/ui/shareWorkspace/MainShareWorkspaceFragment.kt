package com.jeevan.ui.shareWorkspace

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.jeevan.R
import com.jeevan.databinding.FragmentMainShareWorkspaceBinding
import com.jeevan.ui.custom.BottomSheetDialogFragment2
import com.jeevan.utils.Formatter
import com.jeevan.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainShareWorkspaceFragment :
    BottomSheetDialogFragment2(R.layout.fragment_main_share_workspace) {
    private val viewModel: ShareWorkspaceViewModel by hiltNavGraphViewModels(R.id.mainShareWorkspaceFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainShareWorkspaceBinding.bind(view)
        setupViews(binding)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentMainShareWorkspaceBinding) {
        viewModel.shareMessage.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    binding.btnShareWorkspaceShare.isEnabled = true
                    binding.btnShareWorkspaceShare.setOnClickListener { _ ->
                        shareWorkspaceUrl(
                            it.first,
                            it.second.expires_at as String,
                            it.second.workspace.name
                        )
                    }
                    binding.tvShareWorkspaceLink.text = it.first
                }
            } else {
                it.exceptionOrNull()?.let {
                    toast(it.message.toString())
                }
            }
        }

    }

    private fun setupViews(binding: FragmentMainShareWorkspaceBinding) {
        binding.ibShareWorkspaceClose.setOnClickListener {
            dismiss()
        }
    }

    private fun shareWorkspaceUrl(url: String, expires_at: String, workspaceName: String) {
        val text = "You have been invited to workspace '$workspaceName'." +
                " Use the below link to join the workspace.\n\nLink expires " +
                "by ${Formatter.formatDurationISO(expires_at)}.\n\n\nJoin now: $url"

        val intent = Intent().apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, "Invite to less works")
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Invite workspace members"))
    }
}