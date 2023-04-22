package com.naa.sqlite.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.naa.sqlite.R

import com.naa.sqlite.databinding.ItemStudentBinding
import com.naa.sqlite.source.room.entity.StudentData
import com.naa.sqlite.utils.SingleBlock
import com.naa.sqlite.utils.bindItem
import com.naa.sqlite.utils.inflate


class StudentAdapter : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private val ls = ArrayList<StudentData>()
    private var listenerItem: SingleBlock<StudentData>? = null
    private var listenerEdit: SingleBlock<StudentData>? = null
    private var listenerDelete: SingleBlock<StudentData>? = null
    private val binding: ItemStudentBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_student))
    override fun getItemCount(): Int = ls.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun submitList(data: List<StudentData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }


    fun removeItem(data: StudentData){
        val index = ls.indexOfFirst { it.id == data.id }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(data: StudentData){
        val index = ls.indexOfFirst { it.id == data.id }
        ls[index] = data
        notifyItemChanged(index)
    }

    fun insertItem(data: StudentData){
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun setOnItemClickListener(block: SingleBlock<StudentData>) {
        listenerItem = block
    }

    fun setOnItemEditListener(block: SingleBlock<StudentData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<StudentData>) {
        listenerDelete = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init{
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(ls[adapterPosition]) }
                binding?.buttonMore?.setOnClickListener {
                    val menu = PopupMenu(context,it)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.menuDelete -> listenerDelete?.invoke(ls[adapterPosition])
                            R.id.menuEdit -> listenerEdit?.invoke(ls[adapterPosition])
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        fun bind() = bindItem {
            val d = ls[adapterPosition]
            binding?.textTitle?.text = d.firstName
            binding?.textInfo?.text = d.lastName
        }
    }
}