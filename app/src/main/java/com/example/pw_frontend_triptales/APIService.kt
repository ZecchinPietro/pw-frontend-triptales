package com.example.pw_frontend_triptales

import com.example.pw_frontend_triptales.models.Group
import com.example.pw_frontend_triptales.models.LoginRequest
import com.example.pw_frontend_triptales.models.LoginResponse
import com.example.pw_frontend_triptales.models.Post
import com.example.pw_frontend_triptales.models.RegisterRequest
import com.example.pw_frontend_triptales.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/")
    suspend fun getGroups(@Header("Authorization") token: String): List<Group>

    @POST("backend/")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("backend/")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

}
