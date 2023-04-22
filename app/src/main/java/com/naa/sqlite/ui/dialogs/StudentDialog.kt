package com.naa.sqlite.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.naa.sqlite.R
import com.naa.sqlite.databinding.DialogStudentBinding
import com.naa.sqlite.source.room.entity.StudentData
import com.naa.sqlite.utils.SingleBlock


class StudentDialog(context: Context, actionName:String) : AlertDialog(context) {
    private val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_student, null, false)
    private val binding : DialogStudentBinding?= null
    private var listener: SingleBlock<StudentData>? = null
    private var studentData: StudentData? = null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = studentData ?: StudentData()
            data.firstName = binding?.inputFirstName?.text.toString()
            data.lastName = binding?.inputLastName?.text.toString()
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel" ){_,_->}
    }

    fun setStudentData(studentData: StudentData) = with(contentView) {
        this@StudentDialog.studentData = studentData
        binding?.inputFirstName?.setText(studentData.firstName)
        binding?.inputLastName?.setText(studentData.lastName)
    }

    fun setOnClickListener(block: SingleBlock<StudentData>) {
        listener = block
    }
}