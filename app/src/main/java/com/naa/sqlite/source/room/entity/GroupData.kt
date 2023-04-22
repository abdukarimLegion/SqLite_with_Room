package com.naa.sqlite.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = ""
)