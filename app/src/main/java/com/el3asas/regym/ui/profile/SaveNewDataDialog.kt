package com.el3asas.regym.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.databinding.FragmentSaveNewDataDilogBinding
import com.el3asas.regym.utils.setNavigationResult

class SaveNewDataDialog : SheetDialogBinding() {
    private val args by navArgs<ProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentSaveNewDataDilogBinding>(
            inflater,
            R.layout.fragment_save_new_data_dilog,
            container,
            false
        ).apply {
            so3rVal.text = args.bodyStatus
            yes.setOnClickListener {
                setNavigationResult(true, "saveOrNot")
                this@SaveNewDataDialog.dismiss()
            }
            no.setOnClickListener {
                setNavigationResult(false, "saveOrNot")
                this@SaveNewDataDialog.dismiss()
            }
        }.root
    }
}