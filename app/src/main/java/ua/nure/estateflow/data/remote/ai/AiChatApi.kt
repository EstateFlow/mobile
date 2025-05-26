package ua.nure.estateflow.data.remote.ai

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ua.nure.estateflow.data.remote.ai.dto.CreateConversationDto
import ua.nure.estateflow.data.remote.ai.dto.HistoryDto
import ua.nure.estateflow.data.remote.ai.dto.MessageDto
import ua.nure.estateflow.data.remote.ai.dto.MessageRequest

interface AiChatApi {
    @GET("/api/ai/conversations/visible-history")
    suspend fun loadHistory(): Response<HistoryDto>

    @POST("/api/ai/conversations")
    suspend fun createConversation(@Body body: CreateConversationDto): Response<Any>

    @POST("/api/ai/conversations/messages")
    suspend fun sendMessage(@Body body: MessageRequest): Response<MessageDto>
}