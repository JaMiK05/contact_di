package uz.gita.contact_di.data.request

data class AddContactRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
)