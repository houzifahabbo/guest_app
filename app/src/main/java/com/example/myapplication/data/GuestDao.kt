package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
abstract class GuestDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAGuest(guestEntity: Guest)

    @Query("Select * from `guest-table`")
    abstract fun getAllGuests(): Flow<List<Guest>>

    @Query("Select * from `guest-table` where `guest-id`=:id")
    abstract fun getAGuestById(id: String): Flow<Guest>

    @Update
    abstract suspend fun updateAGuest(guestEntity: Guest)

    @Delete
    abstract suspend fun deleteAGuest(guestEntity: Guest)

}