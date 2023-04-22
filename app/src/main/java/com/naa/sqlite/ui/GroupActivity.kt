package com.naa.sqlite.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.naa.sqlite.R
import com.naa.sqlite.databinding.ActivityGroupBinding
import com.naa.sqlite.source.room.AppDataBase
import com.naa.sqlite.ui.adapter.GroupAdapter
import com.naa.sqlite.ui.dialogs.GroupDialog

class GroupActivity : AppCompatActivity() {
    private val adapter = GroupAdapter()
    private val db by lazy { AppDataBase.getDatabase(this) }
    private val groupDao by lazy { db.groupDao() }
    private val studentDao by lazy { db.studentDao() }
    private lateinit var  binding : ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        adapter.submitList(groupDao.getAll())
        adapter.setOnItemClickListener {
            startActivity(Intent(this, StudentActivity::class.java).apply { putExtra("GROUP_ID", it.id) })
        }
        adapter.setOnItemDeleteListener {
            groupDao.delete(it)
            studentDao.deleteAllByGroup(it.id)
            adapter.removeItem(it)
        }
        adapter.setOnItemEditListener {
            val dialog = GroupDialog(this,"Edit")
            dialog.setGroupData(it)
            dialog.setOnClickListener {
                groupDao.update(it)
                adapter.updateItem(it)
            }
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuAdd -> {
                val dialog = GroupDialog(this,"Add")
                dialog.setOnClickListener {
                    val id = groupDao.insert(it)
                    it.id = id
                    adapter.insertItem(it)
                }
                dialog.show()
            }
        }
        return true
    }
}

//26 minut
