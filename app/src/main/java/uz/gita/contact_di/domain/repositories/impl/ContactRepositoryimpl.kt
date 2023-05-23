package uz.gita.contact_di.domain.repositories.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.contact_di.data.request.AddContactRequest
import uz.gita.contact_di.data.response.ResponseAddContact
import uz.gita.contact_di.data.source.remote.api.ContactApi
import uz.gita.contact_di.domain.repositories.ContactRepositories
import javax.inject.Inject

class ContactRepositoryimpl @Inject constructor(
    private val contactApi: ContactApi,
) : ContactRepositories {

    override fun addContact(
        token: String,
        request: AddContactRequest,
    ): Flow<Result<ResponseAddContact>> = flow {

        val response = contactApi.addContact(token, request)

        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() ?: ResponseAddContact(1, "", "", "")))
            }
            else -> {
                emit(Result.failure(Exception("fail")))
            }
        }

    }.catch { }
        .flowOn(Dispatchers.IO)

    override fun getContact(token: String): Flow<Result<List<ResponseAddContact>>> = flow {
        val response = contactApi.getContacts(token)
        val listt = ArrayList<ResponseAddContact>()
        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() ?: listt))
            }
            else -> {
                emit(Result.failure(Exception("fail")))
            }
        }
    }.catch { }
        .flowOn(Dispatchers.IO)

    override fun deleteContact(token: String, id: Int): Flow<Result<String>> =
        flow {

            val response = contactApi.deleteContact(token, id)
            when (response.code()) {
                in 200..299 -> {
                    emit(Result.success(response.body() ?: ""))
                }
                else -> {
                    emit(Result.failure(Exception("fail")))
                }
            }

        }.catch { }.flowOn(Dispatchers.IO)


}