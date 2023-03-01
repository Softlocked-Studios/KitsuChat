package com.softlocked.kitsuchat.net;

import com.softlocked.kitsuchat.core.logging.Logger;
import com.softlocked.kitsuchat.net.server.ConnectionThread;
import com.softlocked.kitsuchat.net.server.ServerThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPDataServer {
    public ServerSocket serverSocket;
    public int port;

    private Thread connectionThread;
    private Thread serverThread;

    private List<Socket> clients = new ArrayList<>();

    public TCPDataServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);

            Logger.info("Server started on port %d", port);

            // 1) Create a new thread to accept incoming connections
            connectionThread = new Thread(new ConnectionThread(serverSocket, clients));
            // 2) Start the thread
            connectionThread.start();

            // 3) Create a new thread to handle incoming data
            serverThread = new Thread(new ServerThread(serverSocket, clients));
            // 4) Start the thread
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();

            connectionThread.interrupt();

            Logger.info("Server stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
