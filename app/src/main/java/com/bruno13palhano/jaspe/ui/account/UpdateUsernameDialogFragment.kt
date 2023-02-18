package com.bruno13palhano.jaspe.ui.account

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bruno13palhano.jaspe.R
import com.google.android.material.textfield.TextInputEditText

class UpdateUsernameDialogFragment(
    private val usernameListener: UsernameListener
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.AlertDialogStyle2)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_update_username, null)
            val usernameView = view.findViewById<TextInputEditText>(R.id.username)

            builder.setView(view)
                .setPositiveButton(R.string.ok_button_label) { _, _ ->
                    val username = usernameView.text.toString()
                    usernameListener.onDialogPositiveClick(username)
                }
                .setNegativeButton(R.string.cancel_button_label) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface UsernameListener {
        fun onDialogPositiveClick(newUsername: String)
    }
}