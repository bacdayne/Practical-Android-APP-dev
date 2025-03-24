package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.sharedpreference.MainActivity

class PreferenceHelper {
    private val PREF_NAME = "user_prefs"

    fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveData(context: Context, username: String, password: String) {
        val editor = getPreferences(context).edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    fun clearData(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

}
