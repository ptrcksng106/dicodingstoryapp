package com.example.mystoryapp.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.models.response.ListStoriesResponse
import com.example.mystoryapp.models.response.ListStoryItem
import com.example.mystoryapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val pref: UserPreference) : ViewModel() {

    private val _getStories = MutableLiveData<List<ListStoryItem>>()
    val getStories: LiveData<List<ListStoryItem>> = _getStories

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getListStoryMap(token: String) {
        ApiConfig.getApiService().getAllStoriesLocation(token)
            .enqueue(object : Callback<ListStoriesResponse>{
                override fun onResponse(
                    call: Call<ListStoriesResponse>,
                    response: Response<ListStoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        _getStories.value = response.body()?.listStory
                    } else {
                        Log.d("onFailure", response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<ListStoriesResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getTheStoriesMap(): LiveData<List<ListStoryItem>> {
        return getStories
    }
}