package server;

import util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Paddy-Gaming on 24.04.2015.
 */
public class RequestHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Server server;

    public RequestHandler(Socket socket) {
        this.socket = socket;
        initialize(socket);
    }

    @Override
    public void run() {
        System.out.println("Thread starts");
        while(!Thread.currentThread().isInterrupted()) {
            String clientRequest = StringUtils.decode(getRequestFromClient());
            System.out.println("clientrequest = " + clientRequest);
            String parsedRequest = parseString(clientRequest);
            System.out.println("Answer for Client : " +  parsedRequest);
            sendAnswerToClient(StringUtils.encode(parsedRequest));
        }
    }

    private void initialize(Socket socket) {
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
        System.out.println("Client connection established!");
    }

    // Bekommt eine Anfrage vom Client
    public String getRequestFromClient() {
        String clientMessage = null;
        try {
            clientMessage = reader.readLine() + "\n";
            System.out.println("Message from Client is: " + clientMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientMessage;
    }

    // Sendet eine Antwort zum Client zurueck
    public void sendAnswerToClient(String answer) {
        try {
            writer.write(answer + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parseString(String inputText) {
        String result = null;

        final Pattern REGEX = Pattern
                .compile("(?<KEYWORD>REVERSE|LOWERCASE|UPPERCASE|SHUTDOWN) (?<STRING>[a-zA-Z0-9]+)\n");

        Matcher matcher = REGEX.matcher(inputText);
        if (!matcher.matches()) {
            if (inputText == "BYE" + '\n') {
                result = "OK BYE";
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result = "Server sagt NEIN!";
        } else {
            String keyword = matcher.group("KEYWORD");
            String text = matcher.group("STRING");
            switch (keyword) {
                case "REVERSE":
                    result = "OK " + reverse(text);
                    break;
                case "UPPERCASE":
                    result = "OK " + uppercase(text);
                    break;
                case "LOWERCASE":
                    result = "OK " + lowercase(text);
                    break;
                case "SHUTDOWN":
                    if (text == "dasspielistausman") {
                        // ToDo
                    } else {
                    }
                    ;
                    break;
            }
        }
        return result;
    }

    private String reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    private String uppercase(String input) {
        return input.toUpperCase();
    }

    private String lowercase(String input) {
        return input.toLowerCase();
    }
}
