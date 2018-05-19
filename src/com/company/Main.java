package com.company;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
            new ProxyThread(serverSocket.accept()).start();
        }
        serverSocket.close();
    }
}

class ProxyThread extends Thread {

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
                LogHelper.writeLog(log);

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

class LogHelper{

    private static final String FILE_NAME = "ProxyHttp.log";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String SEPARATOR = "----------------------------------------------------------";
    private static final File _file= new File(FILE_NAME);

    public static void writeLog(String log) throws IOException {
        StringBuilder logAux = new StringBuilder();

        if(!_file.exists())
            _file.createNewFile();

        logAux.append(getLogHead());
        logAux.append(log);

        Files.write(Paths.get(_file.getName()), logAux.toString().getBytes(), StandardOpenOption.APPEND);
        System.out.println(logAux.toString());
    }

    private static String getLogHead(){
        return SEPARATOR + NEW_LINE + "                     REQUEST" + NEW_LINE + SEPARATOR + NEW_LINE;
    }
}