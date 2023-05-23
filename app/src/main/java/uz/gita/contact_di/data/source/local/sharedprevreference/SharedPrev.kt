package uz.gita.contact_di.data.source.local.sharedprevreference

import android.content.Context
import javax.inject.Inject


class SharedPrev @Inject constructor(val context: Context) : SharedPrefRefrence {

    private val shared = context.getSharedPreferences("locale", Context.MODE_PRIVATE)
    private val edit = shared.edit()!!

    override fun putBoolean(name: String, bol: Boolean) {
        edit.putBoolean(name, bol).apply()
    }

    override fun getBoolean(name: String) = shared.getBoolean(name, false)

    override fun putString(name: String, str: String) {
        edit.putString(name, str).apply()
    }

    override fun getString(name: String): String = shared.getString(name, "").toString()


}