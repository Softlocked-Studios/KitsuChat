package com.softlocked.kitsuchat.net;

import com.softlocked.kitsuchat.core.logging.Logger;

import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    private final String host;
    private final int port;
    private Socket socket;

    private Thread clientThread;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);

            Logger.info("Connected to %s:%d", host, port);

            clientThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            clientThread.start();
        } catch (IOException e) {
            // handle the exception
        }
    }

    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }

            if (clientThread != null) {
                clientThread.interrupt();
            }

            Logger.info("Disconnected from %s:%d", host, port);
        } catch (IOException e) {
            // handle the exception
        }
    }
}