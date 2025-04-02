package com.example.missedcallresponder
import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var autoReplySwitch: Switch
    private lateinit var messageEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS
    )
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autoReplySwitch = findViewById(R.id.autoReplySwitch)
        messageEditText = findViewById(R.id.messageEditText)
        saveButton = findViewById(R.id.saveButton)
        sharedPreferences = getSharedPreferences("AutoReplyPrefs", MODE_PRIVATE)

        // Load saved data
        autoReplySwitch.isChecked = sharedPreferences.getBoolean("autoReply", false)
        messageEditText.setText(sharedPreferences.getString("message", ""))

        saveButton.setOnClickListener {
            val autoReply = autoReplySwitch.isChecked
            val message = messageEditText.text.toString()

            // Save data to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("autoReply", autoReply)
            editor.putString("message", message)
            editor.apply()

            Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show()
        }

        // Kiểm tra và xin quyền
        if (!checkPermissions()) {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
