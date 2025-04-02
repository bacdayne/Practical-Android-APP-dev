import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.udp.R

class MainActivity : AppCompatActivity() {
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var receivedMessages: TextView
    private val udpPort = 12345
    private val serverIp = "192.168.1.22" 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        receivedMessages = findViewById(R.id.receivedMessages)

        // Bắt đầu lắng nghe tin nhắn
        val receiver = UDPReceiver(udpPort) { message ->
            runOnUiThread { receivedMessages.text = message }
        }
        receiver.start()

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            if (message.isNotEmpty()) {
                UDPSender(message, serverIp, udpPort).start()
            }
        }
    }
}
