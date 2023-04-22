package com.naa.sqlite.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.naa.sqlite.R
import com.naa.sqlite.databinding.DialogGroupBinding
import com.naa.sqlite.source.room.entity.GroupData
import com.naa.sqlite.utils.SingleBlock


class GroupDialog(context: Context, actionName:String) : AlertDialog(context) {
    private val contentView : DialogGroupBinding?=null
    private val contentView1 = LayoutInflater.from(context).inflate(R.layout.dialog_group, null, false)
    private var listener: SingleBlock<GroupData>? = null
    private var groupData: GroupData? = null

    init {
        setView(contentView1)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = groupData ?: GroupData()
            data.name = contentView?.inputName?.text.toString()
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ -> }
    }

    fun setGroupData(groupData: GroupData) = with(contentView) {
        this@GroupDialog.groupData = groupData
        contentView?.inputName?.setText(groupData.name)
    }

    fun setOnClickListener(block: SingleBlock<GroupData>) {
        listener = block
    }
}