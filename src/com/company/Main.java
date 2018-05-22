package com.company;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Port Error");
            System.exit(-1);
        }
        while (listening) {
            new Thread(new ProxyThread(serverSocket.accept())).start();
        }
        serverSocket.close();
    }
}
