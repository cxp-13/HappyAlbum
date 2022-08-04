package com.example.happyalbum.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
/**
 * @Author:cxp
 * @Date: 2022/8/3 16:44
 * @Description:点击图片后的对话框
*/

class NoticeDialogFragment(val msg: String?) : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // 构建对话框并设置按钮单击处理程序
            val builder = AlertDialog.Builder(it)
            builder.setMessage(msg)
                .setPositiveButton("确定"
                ) { dialog, id ->
                    // 将肯定按钮事件发送回主机活动
                    listener.onDialogPositiveClick()
                }
                .setNegativeButton("取消"
                ) { dialog, id ->
                    // 将否定按钮事件发送回主机活动
                    listener.onDialogNegativeClick()
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
//按钮事件的接口
    interface NoticeDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }
//重写 Fragment.onAttach（） 方法以实例化 NoticeDialogListener
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