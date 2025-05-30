package ua.nure.estateflow.data.remote.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import ua.nure.estateflow.data.remote.auth.dto.AuthRequest
import ua.nure.estateflow.data.remote.MessageDto
import ua.nure.estateflow.data.remote.auth.dto.ResetPasswordRequest
import ua.nure.estateflow.data.remote.auth.dto.SignInResponse
import ua.nure.estateflow.data.remote.auth.dto.UpdateUserDto
import ua.nure.estateflow.data.remote.auth.dto.UpdateUserRequest
import ua.nure.estateflow.data.remote.auth.dto.UserDto

interface AuthApi {
    @POST("/api/auth/register")
    suspend fun signUp(
        @Body body: AuthRequest
    ): Response<MessageDto>

    @POST("/api/auth/login")
    suspend fun signIn(
        @Body body: AuthRequest
    ): Response<SignInResponse>

    @GET("/api/user")
    suspend fun loadUser(): Response<UserDto>

    @POST("/api/user/password-reset-request")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): Response<MessageDto>

   @PATCH("/api/user")
   suspend fun updateUser(
       @Body body: UpdateUserRequest
   ): Response<UpdateUserDto>
}