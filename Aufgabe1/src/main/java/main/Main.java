package main;

import client.Client;
import server.Server;

/**
 * Created by Paddy-Gaming on 24.04.2015.
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();

        Client client = new Client();
        client.run();
    }
}
