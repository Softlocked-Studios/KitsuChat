package com.softlocked.kitsuchat.net;

import com.softlocked.kitsuchat.core.logging.Logger;
import com.softlocked.kitsuchat.net.server.ServerThread;

import java.net.ServerSocket;

public class TCPDataServer {
    public ServerSocket serverSocket;
    public int port;

    private Thread serverThread;

    public TCPDataServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);

            Logger.info("Server started on port %d", port);

            serverThread = new Thread(new ServerThread(serverSocket));

            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();

            serverThread.interrupt();

            Logger.info("Server stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
