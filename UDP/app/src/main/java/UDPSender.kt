import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UDPSender(private val message: String, private val ip: String, private val port: Int) : Thread() {
    override fun run() {
        try {
            val socket = DatagramSocket()
            val data = message.toByteArray()
            val address = InetAddress.getByName(ip)
            val packet = DatagramPacket(data, data.size, address, port)
            socket.send(packet)
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
