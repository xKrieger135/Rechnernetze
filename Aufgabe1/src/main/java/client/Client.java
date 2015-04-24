package client;

import util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Paddy-Gaming on 24.04.2015.
 */
public class Client implements Runnable{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client() {
        initializeClient();
    }

    private void initializeClient() {
        try {
            this.socket = new Socket("localhost", 30000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer() {
        try {
            writer.write(StringUtils.encode("REVERSE hallo"));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10000);
                System.out.println("Write Hello to Server");
                writeToServer();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
