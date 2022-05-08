package com.example.mystoryapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapp.di.Injection
import com.example.mystoryapp.models.response.ListStoryItem
import com.example.mystoryapp.repository.StoryRepository

class PagingViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getTheListStories(token: String): LiveData<PagingData<ListStoryItem>> = storyRepository.getStories(token).cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PagingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PagingViewModel(Injection.provideRepository(context)) as T
            }
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}