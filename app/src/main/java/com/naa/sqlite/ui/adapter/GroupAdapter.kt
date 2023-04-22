package com.naa.sqlite.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.naa.sqlite.R
import com.naa.sqlite.databinding.ItemGroupBinding
import com.naa.sqlite.source.room.entity.GroupData
import com.naa.sqlite.utils.SingleBlock
import com.naa.sqlite.utils.bindItem
import com.naa.sqlite.utils.inflate

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private val ls = ArrayList<GroupData>()
    private var listenerItem: SingleBlock<GroupData>? = null
    private var listenerEdit: SingleBlock<GroupData>? = null
    private var listenerDelete: SingleBlock<GroupData>? = null

    private val binding: ItemGroupBinding ? = null

    fun submitList(data: List<GroupData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }

    fun removeItem(data: GroupData){
        val index = ls.indexOfFirst { it.id == data.id }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(data: GroupData){
        val index = ls.indexOfFirst { it.id == data.id }
        ls[index] = data
        notifyItemChanged(index)
    }

    fun insertItem(data: GroupData){
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun setOnItemClickListener(block: SingleBlock<GroupData>) {
        listenerItem = block
    }

    fun setOnItemEditListener(block: SingleBlock<GroupData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<GroupData>) {
        listenerDelete = block
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        init {

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
           binding?.textTitle?.text = d.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder=  ViewHolder(parent.inflate(R.layout.item_group))


    override fun getItemCount(): Int  = ls.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}