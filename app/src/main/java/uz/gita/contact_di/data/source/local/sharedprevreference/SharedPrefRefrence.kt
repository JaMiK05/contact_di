package uz.gita.contact_di.data.source.local.sharedprevreference

interface SharedPrefRefrence {

    fun putBoolean(name: String, bol: Boolean)

    fun getBoolean(name: String): Boolean

    fun putString(name: String, str: String)

    fun getString(name: String): String

}