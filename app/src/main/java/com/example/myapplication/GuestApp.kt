package com.example.myapplication

import android.app.Application

class GuestApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}