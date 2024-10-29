package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest-table")
data class Guest(
    @PrimaryKey
    @ColumnInfo(name = "guest-id")
    val _id: String = "",
    @ColumnInfo(name = "guest-name")
    val name: String = "",
    @ColumnInfo(name = "guest-isAttending")
    val isAttending: Boolean = false,
    @ColumnInfo(name = "guest-qrCodeImage")
    val qrCodeImage: String = "",
    @ColumnInfo(name = "guest-v")
    val v: Int = 0
)