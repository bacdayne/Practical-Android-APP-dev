package com.example.firebase_login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Firebase_Login)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnShowData = findViewById<Button>(R.id.btnShowData)
        val tvUserData = findViewById<TextView>(R.id.tvUserData)

        // Đăng ký người dùng
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let {
                                val userData = User(it.uid, email)
                                database.child(it.uid).setValue(userData)
                            }
                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                        } else {
                            val errorMessage = when (task.exception?.message) {
                                "The email address is already in use by another account." ->
                                    "Email đã được đăng ký, vui lòng đăng nhập!"
                                else -> "Đăng ký thất bại: ${task.exception?.message}"
                            }
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }


        // Đăng nhập người dùng
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Đăng nhập thất bại: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        // Hiển thị dữ liệu người dùng
        btnShowData.setOnClickListener {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                database.child(userId).get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val user = snapshot.getValue(User::class.java)
                            Log.d("FirebaseData", "User Data: ${user?.uid}, ${user?.email}") // Debug log
                            tvUserData.text = "ID: ${user?.uid}\nEmail: ${user?.email}"
                        } else {
                            tvUserData.text = "Không tìm thấy dữ liệu người dùng"
                            Log.d("FirebaseData", "Không tìm thấy dữ liệu")
                        }
                    }
                    .addOnFailureListener { exception ->
                        tvUserData.text = "Lỗi khi lấy dữ liệu từ Firebase"
                        Log.e("FirebaseData", "Lỗi: ${exception.message}")
                    }
            } else {
                tvUserData.text = "Người dùng chưa đăng nhập"
                Log.d("FirebaseData", "Người dùng chưa đăng nhập")
            }
        }
    }
}

// Lớp User để lưu thông tin vào Firebase
data class User(val uid: String = "", val email: String = "")
