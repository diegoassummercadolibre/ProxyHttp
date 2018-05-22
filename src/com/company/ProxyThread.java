package com.company;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ProxyThread implements Runnable {

    private final Socket clientSocket;

    public ProxyThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {

        String logRequest = new String();
        String logResponse = new String();

        try {

            // Read request
            InputStream incommingIS = clientSocket.getInputStream();
            byte[] b = new byte[8196];
            int len = incommingIS.read(b);

            if (len > 0) {
                logRequest = new String(b, 0, len);
                String[] logArray = logRequest.split(" ");

                if (logArray[0].equals("GET")){

                    LogHelper.writeLog("REQUEST", logRequest);
                    String host = logArray[3].split("\\r")[0];


                    // Write request
                    Socket socket = new Socket(host, 80);
                    OutputStream outgoingOS = socket.getOutputStream();
                    outgoingOS.write(b, 0, len);


                    // Copy response
                    OutputStream incommingOS = clientSocket.getOutputStream();
                    InputStream outgoingIS = socket.getInputStream();
                    for (int responseLength; (responseLength = outgoingIS.read(b)) != -1; ) {
                        incommingOS.write(b, 0, responseLength);
                    }

                    logResponse = new String(b, StandardCharsets.UTF_8);
                    LogHelper.writeLog("RESPONSE", logResponse);

                    incommingOS.close();
                    outgoingIS.close();
                    outgoingOS.close();
                    incommingIS.close();

                    socket.close();
                }
                else{
                    incommingIS.close();
                }
            }
            else {
                incommingIS.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(logRequest);
            System.out.println(logResponse);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}