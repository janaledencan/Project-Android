package hr.ferit.janaledencan.myurbandictionary

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DictionaryAPI {
    @GET("define?")
    fun getDescription(@Header("x-rapidapi-Key") xRapidApiKey: String,
                       @Header("x-rapidapi-host") xRapidApiHost: String,
                       @Query("term")term: String
    ): retrofit2.Call<DictionaryR>

}