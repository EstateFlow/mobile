package ua.nure.estateflow.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import ua.nure.estateflow.data.datasource.DataSourceResponse

data class Error(
    val message: String?
)

fun parseError(errorBody: ResponseBody?): DataSourceResponse<Error> =
    DataSourceResponse.Error<Any>(
        message = errorBody?.let {
            try {
                GsonBuilder()
                    .setLenient()
                    .create()
                    .fromJson(it.string(), Error::class.java).message
            } catch (ex: JsonSyntaxException) {
                null
            }
        }
    )

fun parseErrorMessage(errorBody: ResponseBody?): String? =
    errorBody?.let {
        try {
            GsonBuilder()
                .setLenient()
                .create()
                .fromJson(it.string(), Error::class.java).message
        } catch (ex: JsonSyntaxException) {
            null
        }
    }