package com.softlocked.kitsuchat.app;

import com.softlocked.kitsuchat.net.TCPClient;
import com.softlocked.kitsuchat.net.TCPDataServer;
import com.softlocked.kitsuchat.net.packets.Packet;

import java.io.IOException;

/**
 * The {@code DataServerApplication} class is the main class of the Kitsu-Backend application.
 *
 * <p> It acts as the "actual" servers used by the chat application. It is responsible for
 * handling all the packets sent by the clients and sending them to the appropriate
 * handlers.
 */
public class DataServerApplication {
    public static void main(String[] args) {
        int port = 25565;
        if(args.length > 0) {

            for(int i = 0; i < args.length; i++) {
                if(args[i].equals("--port")) {
                    if(i + 1 < args.length) {
                        port = Integer.parseInt(args[i + 1]);

                        i++;
                    }
                }
            }
        }
        TCPDataServer server = new TCPDataServer(port);

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));


        // TESTING: CLIENT
        TCPClient client = new TCPClient("localhost", port);
        client.connect();

        // create a thread to send packets
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Packet packet = new Packet((byte) 0x01, new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 });
                try {
                    client.sendPacket(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
