package uz.gita.contact_di.domain.repositories.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.contact_di.data.request.LoginRequest
import uz.gita.contact_di.data.request.RegisterRequest
import uz.gita.contact_di.data.request.VerifyRequest
import uz.gita.contact_di.data.response.LoginResponse
import uz.gita.contact_di.data.response.MessageResponse
import uz.gita.contact_di.data.response.VerifyResponse
import uz.gita.contact_di.data.source.remote.api.AuthApi
import uz.gita.contact_di.domain.repositories.AuthRepositories
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
) : AuthRepositories {

    override fun register(request: RegisterRequest): Flow<Result<MessageResponse>> =
        flow {
            val response = authApi.register(request)

            when (response.code()) {
                in 200..299 -> {
                    emit(Result.success(response.body() ?: MessageResponse("test")))
                }
                else -> {
                    emit(Result.failure(Exception("fail")))
                }
            }

        }
            .catch { }
            .flowOn(Dispatchers.IO)

    override fun login(request: LoginRequest): Flow<Result<LoginResponse>> = flow {

        val response = authApi.login(request)

        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() ?: LoginResponse("null", "null")))
            }
            else -> {
                emit(Result.failure(Exception("Failed")))
            }
        }
    }.catch { }
        .flowOn(Dispatchers.IO)

    override fun verify(request: VerifyRequest): Flow<Result<VerifyResponse>> = flow {

        val response = authApi.verify(request)
        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() ?: VerifyResponse("null", "null")))
            }
            else -> {
                emit(Result.failure(Exception("Failed")))
            }
        }

    }.catch { }
        .flowOn(Dispatchers.IO)

}