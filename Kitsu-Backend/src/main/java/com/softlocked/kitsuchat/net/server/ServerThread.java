package com.softlocked.kitsuchat.net.server;

import java.net.ServerSocket;

public class ServerThread implements Runnable {
    private ServerSocket serverSocket;

    public ServerThread(ServerSocket socket) {
        this.serverSocket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                serverSocket.accept();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
