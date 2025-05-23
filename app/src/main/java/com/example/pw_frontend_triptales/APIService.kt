package com.example.pw_frontend_triptales

import com.example.pw_frontend_triptales.models.Group
import com.example.pw_frontend_triptales.models.LoginRequest
import com.example.pw_frontend_triptales.models.LoginResponse
import com.example.pw_frontend_triptales.models.Post
import com.example.pw_frontend_triptales.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/gruppi/")
    suspend fun getGroups(@Header("Authorization") token: String): List<Group>

    @POST("api/posts/")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body post: Post
    ): Post

    @POST("backend/token/")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("backend/register/")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>
}
