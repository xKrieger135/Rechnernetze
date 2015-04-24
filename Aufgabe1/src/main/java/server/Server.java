package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Paddy-Gaming on 24.04.2015.
 */
public class Server {

    private ServerSocket serverSocket;
    private final int serverPort = 30000;
    private final int maxClientConnections = 3;
    private int currentClientConnections = 0;

    public Server() {
        initializeServer();
    }

    public void startServer() {
        Socket socket = null;
        while(true) {


            if(currentClientConnections <= maxClientConnections) {
                try {
                    socket = serverSocket.accept();
                    new Thread(new RequestHandler(socket)).start();
                    System.out.println("Client connected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutDownServer() {

    }

    public void initializeServer() {
        try {
            this.serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.out.println("Cannot initialize with serverport.");
            e.printStackTrace();
        }
    }
}
