package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Guest
import com.example.myapplication.data.GuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException


data class GuestsViewModelState(
    val isLoading: Boolean = false,
    val guests: List<Guest> = emptyList()
)

class GuestsViewModel(
    private val guestRepository: GuestRepository = Graph.guestRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GuestsViewModelState())
    val state: MutableStateFlow<GuestsViewModelState> = _state


    init {
        fetchGuests()
    }

    fun fetchGuests() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            try {

                val guestList = guestService.getAllGuests()
                guestList.forEach { guest ->
                    val guestInDatabase = guestRepository.getAGuestById(guest._id).firstOrNull()
                    if (guestInDatabase == null) {
                        guestRepository.addAGuest(guest)
                    } else {
                        if (guestInDatabase != guest) {
                            guestRepository.updateAGuest(guest)
                        }
                    }
                    val guestsInDatabase = guestRepository.getAllGuests().first()
                    guestsInDatabase.forEach { dbGuest ->
                        if (!guestList.contains(dbGuest)) {
                            guestRepository.deleteAGuest(dbGuest)
                        }
                    }
                }
                _state.value = state.value.copy(
                    isLoading = false,
                    guests = guestRepository.getAllGuests().first()
                )
            } catch (e: Exception) {
                Log.e("com.example.myapplication.GuestsViewModel", "Error fetching guests", e)
            }
        }
    }

    fun addGuest(guest: Guest) {
        viewModelScope.launch {
            guestRepository.addAGuest(guest)
            fetchGuests()
        }
    }

    fun getAGuestById(id: String): Flow<Guest> {
        return guestRepository.getAGuestById(id)
    }

    fun updateGuest(guest: Guest) {
        viewModelScope.launch {
            guestRepository.updateAGuest(guest)
            try {
                guestService.updateAGuest(guest._id, guest)
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    Log.e(
                        "com.example.myapplication.GuestsViewModel",
                        "Guest not found: ${guest._id}",
                        e
                    )
                } else {
                    Log.e(
                        "com.example.myapplication.GuestsViewModel",
                        "Full response: ${e.message}",
                        e
                    )
                }
            } catch (e: Exception) {
                Log.e("com.example.myapplication.GuestsViewModel", "Full response: ${e.message}", e)
            }
            fetchGuests()
        }
    }
}