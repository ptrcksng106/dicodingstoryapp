package com.example.mystoryapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.mystoryapp.data.StoryPagingSource
import com.example.mystoryapp.database.StoriesDatabase
import com.example.mystoryapp.models.response.ListStoryItem
import com.example.mystoryapp.network.ApiService

class StoryRepository(private val storyDatabase: StoriesDatabase, private val apiService: ApiService) {
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }
}