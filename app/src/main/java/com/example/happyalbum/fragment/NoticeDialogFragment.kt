package com.example.happyalbum.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.happyalbum.R
import com.example.happyalbum.viewmodel.ImageViewModel

class NoticeDialogFragment(val msg: String?) : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(it)
            builder.setMessage(msg)
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the positive button event back to the host activity
                        listener.onDialogPositiveClick()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the negative button event back to the host activity
                        listener.onDialogNegativeClick()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }
}