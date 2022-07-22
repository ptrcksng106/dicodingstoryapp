package com.example.mystoryapp.ui.login


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

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(
            email, password
        )
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                val loginResponse = response.body()

                if (loginResponse != null) {
                    if (!loginResponse.error) {
                        viewModelScope.launch {
                            pref.login()
                        }
                        saveUser(UserModel(loginResponse.loginResult.token, true))
                    }
                } else {
                    saveUser(UserModel("", false))
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