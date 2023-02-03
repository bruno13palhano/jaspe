package com.bruno13palhano.jaspe.ui.search

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.jaspe.ui.common.listOfFilter

class FilterSearchDialogFragment(private val listener: FilterDialogListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var itemPosition = 0
            val builder = AlertDialog.Builder(it, R.style.AlertDialogStyle)
            builder.setTitle(R.string.filter_options_label)
                .setSingleChoiceItems(R.array.array_filter_options, 0) { _, checkedItem ->
                    itemPosition = checkedItem
                }
                .setPositiveButton(R.string.ok_button_label) { _, _ ->
                    listener.onDialogPositiveClick(listOfFilter[itemPosition])
                }
                .setNegativeButton(R.string.cancel_button_label) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface FilterDialogListener {
        fun onDialogPositiveClick(item: String)
    }
}