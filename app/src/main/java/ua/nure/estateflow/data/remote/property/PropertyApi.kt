package ua.nure.estateflow.data.remote.property



import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ua.nure.estateflow.data.remote.property.dto.PropertyDto

interface PropertyApi {
    @GET("api/properties")
    suspend fun load(
        @Query("filter") filter: String = "active"
    ): Response<List<PropertyDto>>
}