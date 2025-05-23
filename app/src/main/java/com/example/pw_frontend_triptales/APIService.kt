package com.example.pw_frontend_triptales

import com.example.pw_frontend_triptales.models.Group
import com.example.pw_frontend_triptales.models.Post
import retrofit2.http.*

interface ApiService {

    @GET("api/gruppi/")
    suspend fun getGroups(@Header("Authorization") token: String): List<Group>

    @POST("api/posts/")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body post: Post
    ): Post

}