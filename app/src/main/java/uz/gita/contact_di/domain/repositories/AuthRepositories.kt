package uz.gita.contact_di.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.gita.contact_di.data.request.LoginRequest
import uz.gita.contact_di.data.request.RegisterRequest
import uz.gita.contact_di.data.request.VerifyRequest
import uz.gita.contact_di.data.response.LoginResponse
import uz.gita.contact_di.data.response.MessageResponse
import uz.gita.contact_di.data.response.VerifyResponse

interface AuthRepositories {

    fun register(request: RegisterRequest): Flow<Result<MessageResponse>>

    fun login(request: LoginRequest): Flow<Result<LoginResponse>>

    fun verify(request: VerifyRequest): Flow<Result<VerifyResponse>>


}