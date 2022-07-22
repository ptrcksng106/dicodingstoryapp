package com.example.mystoryapp.ui.main

import androidx.lifecycle.*
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(
    private val pref: UserPreference
) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}