package com.example.mystoryapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapp.di.Injection
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.models.response.ListStoriesResponse
import com.example.mystoryapp.models.response.ListStoryItem
import com.example.mystoryapp.network.ApiConfig
import com.example.mystoryapp.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getStories = MutableLiveData<List<ListStoryItem>>()
    val getStories: LiveData<List<ListStoryItem>> = _getStories

//    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
//        return storyRepository.getStories(token).cachedIn(viewModelScope).asFlow().asLiveData()
//    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

//    fun getTheListStory(token : String): LiveData<PagingData<ListStoryItem>> = storyRepository.getStories(token).cachedIn(viewModelScope)

//    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
//        override fun <T: ViewModel> create(modelClass: Class<T>): T {
//            if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return MainViewModel(Injection.provideRepository(context)) as T
//            }
//            else throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }

//    val stories: LiveData<PagingData<>>

//    fun getListStories(token: String) {
//        _isLoading.value = true
//        val client =
//            ApiConfig.getApiService().getStories(token)
//                .enqueue(object : Callback<ListStoriesResponse> {
//                    override fun onResponse(
//                        call: Call<ListStoriesResponse>,
//                        response: Response<ListStoriesResponse>
//                    ) {
//                        _isLoading.value = false
//
//                        if (response.isSuccessful) {
//                            _getStories.value = response.body()?.listStory
//                        } else {
//                            Log.d("onFailure", response.body()?.message.toString())
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ListStoriesResponse>, t: Throwable) {
//                        Log.d("onFailure", t.message.toString())
//                    }
//
//                })
//    }

    fun getTheStories(): LiveData<List<ListStoryItem>> {
        return getStories
    }

}