package com.company;

import java.io.*;
import java.net.Socket;

public class ProxyThread extends Thread {

    private final Socket clientSocket;

    public ProxyThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {

        String log = new String();

        try {

            // Read request
            InputStream incommingIS = clientSocket.getInputStream();
            byte[] b = new byte[8196];
            int len = incommingIS.read(b);

            if (len > 0) {
                log = new String(b, 0, len);
                String[] logArray = log.split(" ");

                if (logArray[0].equals("GET")){

                    LogHelper.writeLog(log);

                    String host = logArray[3].split("\\r")[0];

                    // Write request
                    Socket socket = new Socket(host, 80);
                    OutputStream outgoingOS = socket.getOutputStream();
                    outgoingOS.write(b, 0, len);

                    // Copy response
                    OutputStream incommingOS = clientSocket.getOutputStream();
                    InputStream outgoingIS = socket.getInputStream();
                    InputStream is = socket.getInputStream();
                    for (int length; (length = outgoingIS.read(b)) != -1; ) {
                        incommingOS.write(b, 0, length);
                    }


                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }

                    rd.close();
                    LogHelper.writeLog(response.toString());

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
            System.out.println(log);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}