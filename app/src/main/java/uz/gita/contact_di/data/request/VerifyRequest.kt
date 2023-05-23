package uz.gita.contact_di.data.request

data class VerifyRequest(
    val phone: String,
    val code: String,
)
