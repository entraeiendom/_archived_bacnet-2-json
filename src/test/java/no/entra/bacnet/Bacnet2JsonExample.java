package no.entra.bacnet;

import no.entra.bacnet.json.Bacnet2Json;

import java.io.IOException;
import java.net.*;

public class Bacnet2JsonExample {

    private static byte[] buf = new byte[2048];
    public static final int BACNET_DEFAULT_PORT = 47808;

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(null);
            SocketAddress inetAddress = new InetSocketAddress(BACNET_DEFAULT_PORT);
            socket.bind(inetAddress);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] receivedBytes = packet.getData();
                String hexString = Byte2HexConverter.integersToHex(receivedBytes);
                System.out.println("Bacnet as hexString: " + hexString);
                String bacnetJson = Bacnet2Json.hexStringToJson(hexString);
                System.out.println("Bacnet Parsed to Json: " + bacnetJson);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}