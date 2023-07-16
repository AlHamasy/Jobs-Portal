package com.asad.jobsportal.data.datastore

import android.content.Context

class SharedPreferences(context: Context) {

    companion object {
        private const val PREF_NAME = "user_preferences"
        private const val LOGIN_KEY = "login"
        private const val NAMA_KEY = "nama"
        private const val URL_FOTO_KEY = "url_foto"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveLoginInfo(nama: String, urlFoto: String) {
        val editor = sharedPreferences.edit()
        editor.putString(NAMA_KEY, nama)
        editor.putString(URL_FOTO_KEY, urlFoto)
        editor.apply()
    }

    fun getLoginInfo(): LoginInfo {
        val nama = sharedPreferences.getString(NAMA_KEY, "") ?: ""
        val urlFoto = sharedPreferences.getString(URL_FOTO_KEY, "") ?: ""
        return LoginInfo(nama, urlFoto)
    }

    fun clearLoginInfo() {
        val editor = sharedPreferences.edit()
        editor.remove(NAMA_KEY)
        editor.remove(URL_FOTO_KEY)
        editor.apply()
    }
}

data class LoginInfo(val nama: String, val urlFoto: String)
