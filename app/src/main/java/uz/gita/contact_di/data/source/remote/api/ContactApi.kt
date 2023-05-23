package uz.gita.contact_di.data.source.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.contact_di.data.request.AddContactRequest
import uz.gita.contact_di.data.response.ResponseAddContact

interface ContactApi {

    @POST("/api/v1/contact")
    suspend fun addContact(
        @Header("token") token: String,
        @Body addContactRequest: AddContactRequest,
    ): Response<ResponseAddContact>

    @GET("/api/v1/contact")
    suspend fun getContacts(@Header("token") token: String): Response<List<ResponseAddContact>>

    @DELETE("/api/v1/contact")
    suspend fun deleteContact(
        @Header("token") token: String,
        @Body delete: Int,
    ): Response<String>

}