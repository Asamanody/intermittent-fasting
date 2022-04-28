package com.el3asas.regym.ui.profile.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.databinding.FragmentEffortDialogBinding
import com.el3asas.regym.ui.profile.ProfileFragmentArgs
import com.el3asas.regym.utils.setNavigationResult

class EffortDialog : SheetDialogBinding() {
    private val args by navArgs<ProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding<FragmentEffortDialogBinding>(
            inflater,
            R.layout.fragment_effort_dialog,
            container,
            false
        ).apply {

            when (args.effort) {
                getString(R.string.level1) -> {
                    jobStatus.check(R.id.level1)
                }
                getString(R.string.level2) -> {
                    jobStatus.check(R.id.level2)
                }
                getString(R.string.level3) -> {
                    jobStatus.check(R.id.level3)
                }
            }

            go.setOnClickListener {
                when (jobStatus.checkedRadioButtonId) {
                    level1.id ->
                        setNavigationResult(getString(R.string.level1), "effort")
                    level2.id ->
                        setNavigationResult(getString(R.string.level2), "effort")
                    level3.id ->
                        setNavigationResult(getString(R.string.level3), "effort")
                }
                this@EffortDialog.dismiss()
            }
            executePendingBindings()
        }.root
    }
}