package ua.nure.estateflow.data.remote.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ua.nure.estateflow.data.remote.auth.dto.AuthRequest

interface AuthApi {
    @POST("register")
    suspend fun signUp(
        @Body body: AuthRequest
    ): Response<Any>
}