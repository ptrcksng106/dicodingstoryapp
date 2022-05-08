package com.example.mystoryapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.models.response.ListStoryItem
import com.example.mystoryapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getDetailStories = MutableLiveData<ListStoryItem>()
    val getDetailStories: LiveData<ListStoryItem> = _getDetailStories

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getDetailStory(token: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailStories(token).enqueue(object : Callback<ListStoryItem> {
            override fun onResponse(call: Call<ListStoryItem>, response: Response<ListStoryItem>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _getDetailStories.value = response.body()
                } else {
                    Log.d("onFailure", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ListStoryItem>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun getTheDetailStory(): LiveData<ListStoryItem> {
        return getDetailStories
    }
}