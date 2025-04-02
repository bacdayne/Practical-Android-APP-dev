import java.net.DatagramPacket
import java.net.DatagramSocket

class UDPReceiver(private val port: Int, private val callback: (String) -> Unit) : Thread() {
    override fun run() {
        try {
            val socket = DatagramSocket(port)
            val buffer = ByteArray(1024)
            while (true) {
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val message = String(packet.data, 0, packet.length)
                callback(message) // Cập nhật giao diện thông qua callback
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
