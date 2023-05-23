package uz.gita.contact_di.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.gita.contact_di.data.request.AddContactRequest
import uz.gita.contact_di.data.request.DeleteContactRequest
import uz.gita.contact_di.data.response.ResponseAddContact

interface ContactRepositories {

    fun addContact(token: String, request: AddContactRequest): Flow<Result<ResponseAddContact>>

    fun getContact(token: String): Flow<Result<List<ResponseAddContact>>>

    fun deleteContact(token: String, id: Int): Flow<Result<String>>

}