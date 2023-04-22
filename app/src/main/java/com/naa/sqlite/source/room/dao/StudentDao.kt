package com.naa.sqlite.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.naa.sqlite.source.room.entity.StudentData

@Dao
interface StudentDao : BaseDao<StudentData> {

    @Query("SELECT * FROM StudentData")
    fun getAll() : List<StudentData>

    @Query("DELETE FROM StudentData WHERE groupId=:id")
    fun deleteAllByGroup(id: Long)

    @Query("SELECT * FROM StudentData WHERE groupId=:groupId")
    fun getStudentsByGroupId(groupId: Long): List<StudentData>
}