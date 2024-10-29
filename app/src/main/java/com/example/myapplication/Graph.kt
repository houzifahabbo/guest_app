package com.example.myapplication

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.GuestDatabase
import com.example.myapplication.data.GuestRepository

object Graph {

    lateinit var database: GuestDatabase

    val guestRepository by lazy {
        GuestRepository(guestDao = database.guestDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, GuestDatabase::class.java, "guest.db").build()
    }
}