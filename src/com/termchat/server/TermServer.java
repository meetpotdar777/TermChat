package com.termchat.server;

import com.termchat.shared.Constants;
import java.net.*;
import java.io.*;
import java.util.*;

public class TermServer {
    public static Map<String, PrintWriter> clientMap = Collections.synchronizedMap(new HashMap<>());
    private static final String LOG_FILE = "chat_history.txt";

    public static void main(String[] args) {
        System.out.println(Constants.GREEN + Constants.LOGO + Constants.RESET);
        System.out.println("TermChat Server active on Port: " + Constants.PORT);

        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }

    public static void logToFile(String message) {
        // Remove ANSI colors before saving to file
        String cleanMessage = message.replaceAll("\u001B\\[[;\\d]*m", "");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)))) {
            out.println(cleanMessage);
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }

    public static void broadcast(String message) {
        logToFile(message); 
        synchronized (clientMap) {
            for (PrintWriter writer : clientMap.values()) {
                writer.println(message);
            }
        }
    }
}