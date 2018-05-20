package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                //LogHelper.writeLog(log);

                String[] logAux = log.split(" ");

                String host = logAux[3].split("\\r")[0];
                int port = 80;
                if (logAux[0].contains("CONNECT")){
                    String[] hostAndPort = host.split(":");
                    host = hostAndPort[0];
                    port = Integer.parseInt(hostAndPort[1]);
                }

                // Write request
                Socket socket = new Socket(host, port);
                OutputStream outgoingOS = socket.getOutputStream();
                outgoingOS.write(b, 0, len);

                // Copy response
                OutputStream incommingOS = clientSocket.getOutputStream();
                InputStream outgoingIS = socket.getInputStream();
                for (int length; (length = outgoingIS.read(b)) != -1; ) {
                    incommingOS.write(b, 0, length);
                }



                incommingOS.close();
                outgoingIS.close();
                outgoingOS.close();
                incommingIS.close();

                socket.close();
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