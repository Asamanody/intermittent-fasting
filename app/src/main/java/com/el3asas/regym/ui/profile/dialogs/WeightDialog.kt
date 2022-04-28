package com.el3asas.regym.ui.profile.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.databinding.FragmentNumberDialogBinding
import com.el3asas.regym.ui.profile.ProfileFragmentArgs
import com.el3asas.regym.utils.setNavigationResult

class WeightDialog : SheetDialogBinding() {
    private val args by navArgs<ProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding<FragmentNumberDialogBinding>(
            inflater,
            R.layout.fragment_number_dialog,
            container,
            false
        ).apply {

            DialogTitle.text = getString(R.string.enterweight)
            numberPicker.value = args.weight

            go.setOnClickListener {
                setNavigationResult(numberPicker.value.toString(), "weight")
                this@WeightDialog.dismiss()
            }
            executePendingBindings()
        }.root

    }

}