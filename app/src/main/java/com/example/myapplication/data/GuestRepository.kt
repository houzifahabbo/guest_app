package com.example.myapplication.data

class GuestRepository(private val guestDao: GuestDao) {

    suspend fun addAGuest(guest: Guest) {
        guestDao.addAGuest(guest)
    }

    fun getAllGuests() = guestDao.getAllGuests()

    fun getAGuestById(id: String) = guestDao.getAGuestById(id)

    suspend fun updateAGuest(guest: Guest) {
        guestDao.updateAGuest(guest)
    }

    suspend fun deleteAGuest(guest: Guest) {
        guestDao.deleteAGuest(guest)
    }
}