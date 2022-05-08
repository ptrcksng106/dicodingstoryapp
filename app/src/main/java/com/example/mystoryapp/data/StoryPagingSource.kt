package com.example.mystoryapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mystoryapp.models.response.ListStoryItem
import com.example.mystoryapp.network.ApiService
import com.example.mystoryapp.viewmodel.MainViewModel
import java.lang.Exception


class StoryPagingSource(private val apiService: ApiService,private val token:String): PagingSource<Int, ListStoryItem>() {


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(token,page,params.loadSize)


            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception : Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPostion ->
            val anchorPage = state.closestPageToPosition(anchorPostion)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}