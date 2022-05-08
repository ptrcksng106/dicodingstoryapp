package com.example.mystoryapp.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.models.response.LoginResponse
import com.example.mystoryapp.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }


    fun checker(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }


    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(
            name, email, password
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                val registerResponse = response.body()
                if (registerResponse != null) {
                    checker(UserModel("", false))
                }

            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }
}