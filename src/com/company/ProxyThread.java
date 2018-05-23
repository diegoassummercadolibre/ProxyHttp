package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxyThread implements Runnable {

    private final Socket clientSocket;

    public ProxyThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {

        String logRequest = new String();
        String logResponse = new String();

        try {

            InputStream incommingIS = clientSocket.getInputStream();
            byte[] b = new byte[8196];
            int len = incommingIS.read(b);

            if (len > 0) {
                logRequest = new String(b, 0, len);
                Matcher get = Pattern.compile("^GET").matcher(logRequest);

                if(get.find()){

                    LogHelper.writeLog("REQUEST", logRequest);
                    Matcher hostMatch = Pattern.compile("Host: (.*)").matcher(logRequest);
                    hostMatch.find();
                    String host = hostMatch.group(1);

                    Socket socket = new Socket(host, 80);
                    OutputStream outgoingOS = socket.getOutputStream();
                    outgoingOS.write(b, 0, len);

                    OutputStream incommingOS = clientSocket.getOutputStream();
                    InputStream outgoingIS = socket.getInputStream();
                    for (int responseLength; (responseLength = outgoingIS.read(b)) != -1; ) {
                        incommingOS.write(b, 0, responseLength);
                    }

                    logResponse = new String(b);
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