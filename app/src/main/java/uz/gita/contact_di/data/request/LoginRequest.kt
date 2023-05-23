package uz.gita.contact_di.data.request

data class LoginRequest(
    val phone: String,
    val password: String,
)