package main;

import client.Client;
import server.Server;

/**
 * Created by Paddy-Gaming on 24.04.2015.
 */
public class MainServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
