package com.naa.sqlite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.naa.sqlite.R
import com.naa.sqlite.databinding.ActivityStudentBinding
import com.naa.sqlite.source.room.AppDataBase
import com.naa.sqlite.ui.adapter.StudentAdapter
import com.naa.sqlite.ui.dialogs.StudentDialog

class StudentActivity : AppCompatActivity() {
    private val adapter = StudentAdapter()
    private val db by lazy { AppDataBase.getDatabase(this) }
    private val studentDao by lazy { db.studentDao() }
    private val groupId by lazy { intent.extras?.getLong("GROUP_ID") ?: 0 }
private lateinit var binding : ActivityStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        adapter.submitList(studentDao.getStudentsByGroupId(groupId))
        adapter.setOnItemDeleteListener {
            studentDao.delete(it)
            adapter.removeItem(it)
        }
        adapter.setOnItemEditListener {
            val dialog = StudentDialog(this,"Edit")
            dialog.setStudentData(it)
            dialog.setOnClickListener {
                studentDao.update(it)
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
            android.R.id.home -> finish()
            R.id.menuAdd -> {
                val dialog = StudentDialog(this,"Add")
                dialog.setOnClickListener {
                    it.groupId = groupId
                    val id = studentDao.insert(it)
                    it.id = id
                    adapter.insertItem(it)
                }
                dialog.show()
            }
        }
        return true
    }
}