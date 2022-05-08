package com.example.mystoryapp.network


import com.example.mystoryapp.models.response.ListStoriesResponse
import com.example.mystoryapp.models.response.ListStoryItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<com.example.mystoryapp.models.response.LoginResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<com.example.mystoryapp.models.response.LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ListStoriesResponse

    @GET("stories?location=1")
    fun getAllStoriesLocation(
        @Header("Authorization") token: String
    ): Call<ListStoriesResponse>


    @GET("stories")
    fun getDetailStories(
        @Header("Authorization") token: String
    ): Call<ListStoryItem>

    @Multipart
    @POST("stories")
    fun uploadStories(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<com.example.mystoryapp.models.response.LoginResponse>

}