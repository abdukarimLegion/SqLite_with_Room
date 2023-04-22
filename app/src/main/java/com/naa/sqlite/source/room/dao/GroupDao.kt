package com.naa.sqlite.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.naa.sqlite.source.room.entity.GroupData

@Dao
interface GroupDao : BaseDao<GroupData> {
    @Query("SELECT * FROM GroupData")
    fun getAll():List<GroupData>
}