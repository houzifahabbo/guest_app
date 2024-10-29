package com.example.myapplication

import com.example.myapplication.data.Guest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .baseUrl("https://guest-list-api.vercel.app/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val guestService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("guest")
    suspend fun getAllGuests(): List<Guest>

    @PUT("guest/{id}")
    suspend fun updateAGuest(@Path("id") id: String, @Body guest: Guest): Guest

}