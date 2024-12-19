package com.example.gouvernorats

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/Demande/hebergementsDisponibles")
    suspend fun getHebergementsDisponibles(): Response<List<HotelPackage>>

    @GET("/Demande/hebergement/{id}/images")
    suspend fun getImagesByHebergementId(@Path("id") id: Long): Response<List<String>>

    // Nouvelle méthode pour récupérer un hébergement par ID
    @GET("/Demande/hebergementById/{id}")
    suspend fun getHebergementById(@Path("id") id: Long): Response<HotelPackage>

    @GET("/Demande/hebergements/search")
    suspend fun searchHebergementsByLocalisation(@Query("localisation") localisation: String): Response<List<HotelPackage>>

    @GET("/Demande/ListeDesChambresDisponibles/{hebergementId}")
    suspend fun getChambresByHebergementId(@Path("hebergementId") id: Long): Response<List<ChambrePackage>>

    @POST("/api/reservations/{id}")
    suspend fun createReservation(@Path("id") id: Long?, @Body reservation: ReservationPackage): Response<ReservationPackage>

}