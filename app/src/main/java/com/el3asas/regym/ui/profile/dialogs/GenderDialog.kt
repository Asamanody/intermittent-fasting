package com.el3asas.regym.ui.profile.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.databinding.FragmentGenderDialogBinding
import com.el3asas.regym.ui.profile.ProfileFragmentArgs
import com.el3asas.regym.utils.setNavigationResult

class GenderDialog : SheetDialogBinding() {
    private val args by navArgs<ProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding<FragmentGenderDialogBinding>(
            inflater,
            R.layout.fragment_gender_dialog,
            container,
            false
        ).apply {
            when (args.gender) {
                getString(R.string.male) -> {
                    gender.check(R.id.male)
                }
                getString(R.string.female) -> {
                    gender.check(R.id.female)
                }
            }
            executePendingBindings()

            go.setOnClickListener {
                when (gender.checkedRadioButtonId) {
                    male.id ->
                        setNavigationResult(getString(R.string.male), "gender")
                    female.id ->
                        setNavigationResult(getString(R.string.female), "gender")
                }
                this@GenderDialog.dismiss()
            }
        }.root
    }
}