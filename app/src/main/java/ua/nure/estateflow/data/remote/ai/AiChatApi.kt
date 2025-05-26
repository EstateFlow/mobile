package ua.nure.estateflow.data.remote.ai

import retrofit2.Response
import retrofit2.http.GET

interface AiChatApi {
    @GET("/api/ai/conversations/history")
    suspend fun loadConversations(): Response<Any>
}