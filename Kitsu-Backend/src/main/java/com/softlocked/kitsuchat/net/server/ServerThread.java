package com.softlocked.kitsuchat.net.server;

import com.softlocked.kitsuchat.core.logging.Logger;
import com.softlocked.kitsuchat.net.packets.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ServerThread implements Runnable {
    private final ServerSocket serverSocket;
    private final List<Socket> clients;

    public ServerThread(ServerSocket socket, List<Socket> clients) {
        this.serverSocket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            // First, check if any clients have disconnected
            for (Socket client : clients) {
                if (client.isClosed()) {
                    Logger.info("Client disconnected from %s:%d", client.getInetAddress().getHostAddress(), client.getPort());
                    clients.remove(client);
                }
            }

            // Then, read data from each client
            for (Socket client : clients) {
                try {
                    InputStream stream = client.getInputStream();

                    client.setSoTimeout(1);

                    // read packet
                    Packet packet = read(stream);

                    if (packet != null) {
                        Logger.info("Received packet from %s:%d", client.getInetAddress().getHostAddress(), client.getPort());
                        Logger.info("Packet: %s", packet.toString());
                    }
                } catch (Exception ignored) {}
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
    }

    public Packet read(InputStream input) throws IOException {
        // read the packet length (4 bytes)
        byte[] lengthBytes = new byte[4];
        int read = input.read(lengthBytes);
        if (read < 4) {
            throw new IOException("Failed to read packet length");
        }
        int length = ByteBuffer.wrap(lengthBytes).getInt();

        // read the packet data based on the length
        byte[] dataBytes = new byte[length];
        read = input.read(dataBytes);
        if (read < length) {
            throw new IOException("Failed to read packet data");
        }

        // construct and return the packet
        List<Byte> data = new ArrayList<>();
        for (byte b : dataBytes) {
            data.add(b);
        }
        byte id = data.remove(0);
        return new Packet(id, data);
    }
}
