package com.example.sharedpreference
import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
   private val Prefs_name = "user_pref"
   private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Prefs_name, Context.MODE_PRIVATE)
   fun saveData(user: String, password: String) {
       sharedPreferences.edit().apply(){
           putString("user", user)
           putString("password", password)
           apply()
       }
   }
   fun getUser(): String? {
       return sharedPreferences.getString("user", null)
   }
   fun getPassword(): String? {
       return sharedPreferences.getString("password", null)
   }
    fun clearData() {
         sharedPreferences.edit().clear().apply()
    }
}