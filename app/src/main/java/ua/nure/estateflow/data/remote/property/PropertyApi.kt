package ua.nure.estateflow.data.remote.property


import retrofit2.Response
import retrofit2.http.GET
import ua.nure.estateflow.data.remote.property.dto.PropertyDto

interface PropertyApi {
    @GET("properties")
    suspend fun load(

    ): Response<List<PropertyDto>>
}