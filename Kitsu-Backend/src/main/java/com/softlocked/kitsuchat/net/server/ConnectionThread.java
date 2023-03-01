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

public class ConnectionThread extends Thread {
    private final ServerSocket serverSocket;
    private final List<Socket> clients;

    public ConnectionThread(ServerSocket socket, List<Socket> clients) {
        this.serverSocket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Accept incoming connections and get a socket object
                Socket socket = serverSocket.accept();

                Logger.info("Client connected from %s:%d", socket.getInetAddress().getHostAddress(), socket.getPort());

                clients.add(socket);
            } catch (Exception ignored) {
            }

            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }
        }
    }
}
