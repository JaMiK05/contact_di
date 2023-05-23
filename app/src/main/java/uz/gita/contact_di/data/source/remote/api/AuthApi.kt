package uz.gita.contact_di.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.contact_di.data.request.LoginRequest
import uz.gita.contact_di.data.request.RegisterRequest
import uz.gita.contact_di.data.request.VerifyRequest
import uz.gita.contact_di.data.response.LoginResponse
import uz.gita.contact_di.data.response.MessageResponse
import uz.gita.contact_di.data.response.VerifyResponse

interface AuthApi {

    @POST("api/v1/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<MessageResponse>

    @POST("/api/v1/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/register/verify")
    suspend fun verify(@Body request: VerifyRequest): Response<VerifyResponse>

}