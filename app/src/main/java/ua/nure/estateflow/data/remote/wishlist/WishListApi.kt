package ua.nure.estateflow.data.remote.wishlist

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import ua.nure.estateflow.data.remote.wishlist.dto.WishListDto

interface WishListApi {
    @POST("/api/wishlist")
    suspend fun add(
        @Body body: WishListDto
    ): Response<Any>

    @DELETE("/api/wishlist/{propertyId}")
    suspend fun delete(
        @Path("propertyId") propertyId: String
    ): Response<Any>
}