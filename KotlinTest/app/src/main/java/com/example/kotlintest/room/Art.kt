package com.example.kotlintest.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
        var name : String,
        var artistName : String,
        var year : Int,
        var flag : String,
        @PrimaryKey(autoGenerate = true)
        var myId : Int? = null
)
