package com.example.bai2autoreply
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.CallLog
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class Broadcast : BroadcastReceiver() {

    companion object {
        private var lastSentNumber: String? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Check the phone state
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

            if (TelephonyManager.EXTRA_STATE_IDLE == state) {
                Log.d("sms", "Call ended. Checking for missed calls...")
                val lastMissedCall = getLastMissedCall(context)

                if (lastMissedCall != null && lastMissedCall != lastSentNumber) {
                    Log.d("sms", "Missed call from: $lastMissedCall")
                    sendSMS(context, lastMissedCall, "Xin chào, tôi là Hoàng Thị Phương, sẽ gọi lại sau.")
                    lastSentNumber = lastMissedCall // Mark the number as sent
                }
            }
        }
    }

    private fun getLastMissedCall(context: Context): String? {
        var cursor: Cursor? = null
        var lastMissedNumber: String? = null

        try {
            cursor = context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                arrayOf(CallLog.Calls.NUMBER),
                "${CallLog.Calls.TYPE} = ? AND ${CallLog.Calls.NEW} = 1",
                arrayOf(CallLog.Calls.MISSED_TYPE.toString()),
                "${CallLog.Calls.DATE} DESC" // Sort by date in descending order
            )

            if (cursor != null && cursor.moveToFirst()) {
                lastMissedNumber = cursor.getString(0) // Get the phone number of the most recent missed call
            }
        } catch (e: Exception) {
            Log.e("CallLog", "Error retrieving missed calls", e)
        } finally {
            cursor?.close() // Close the Cursor after use
        }

        return lastMissedNumber
    }

    private fun sendSMS(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("SMS", "Message sent")
        } catch (e: Exception) {
            Log.e("SMS", "Error sending message", e)
        }
    }
}
