package com.example.sharedpreference

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharedpreference.ui.theme.SharedPreferenceTheme
import com.example.sharedpreferences.PreferenceHelper

class MainActivity : ComponentActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtPw: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var txtInfo: TextView
    private lateinit var btnShow: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //anh xa
        edtName = findViewById(R.id.edt_name)
        edtPw = findViewById(R.id.edt_pw)
        btnSave = findViewById(R.id.btn_Save)
        btnDelete = findViewById(R.id.btn_delete)
        btnShow = findViewById(R.id.btn_Show)
        txtInfo = findViewById(R.id.txt_info)

        //xu ly su kien
        btnSave.setOnClickListener {
            val username = edtName.text.toString()
            val password = edtPw.text.toString()
            //luu du lieu vao share preference
            val editor = getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()
            if (edtName.text.isEmpty() || edtPw.text.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show()

            }
        }
        btnDelete.setOnClickListener {
            val editor = getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
            editor.clear()
            editor.apply()
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show()
            edtName.setText("")
            edtPw.setText("")
            txtInfo.text = ""
        }
        btnShow.setOnClickListener {
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")
            val password = sharedPreferences.getString("password", "")
            edtName.setText(username)
            edtPw.setText(password)
            txtInfo.text = "Username: $username Password: $password"


        }
    }
}

