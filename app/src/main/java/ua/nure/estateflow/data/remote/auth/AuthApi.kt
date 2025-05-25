package ua.nure.estateflow.data.remote.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ua.nure.estateflow.data.remote.auth.dto.AuthRequest
import ua.nure.estateflow.data.remote.auth.dto.AuthResponse
import ua.nure.estateflow.data.remote.auth.dto.SignInResponse
import ua.nure.estateflow.data.remote.auth.dto.UserDto

interface AuthApi {
    @POST("/api/auth/register")
    suspend fun signUp(
        @Body body: AuthRequest
    ): Response<AuthResponse>

    @POST("/api/auth/login")
    suspend fun signIn(
        @Body body: AuthRequest
    ): Response<SignInResponse>

    @GET("/api/user")
    suspend fun loadUser(): Response<UserDto>
}