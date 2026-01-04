package com.termchat.server;

import com.termchat.shared.Constants;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ClientHandler extends Thread {
    private Socket socket;
    private String username;
    private PrintWriter out;

    public ClientHandler(Socket socket) { 
        this.socket = socket; 
    }

    // Returns time in [HH:mm] format
    private String getTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // Scans message for banned words and replaces them with asterisks
    private String filterMessage(String input) {
        String filtered = input;
        for (String word : Constants.BANNED_WORDS) {
            if (filtered.toLowerCase().contains(word.toLowerCase())) {
                String stars = "*".repeat(word.length());
                // Case-insensitive replacement
                filtered = filtered.replaceAll("(?i)" + word, stars);
            }
        }
        return filtered;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // 1. Receive Username
            this.username = in.readLine();
            
            if (username != null && !username.trim().isEmpty()) {
                TermServer.clientMap.put(username, out);
                
                // --- WELCOME SEQUENCE ---
                out.println(Constants.CYAN + Constants.LOGO + Constants.RESET);
                out.println(Constants.GREEN + "Welcome to the terminal, " + username + "!" + Constants.RESET);
                out.println(Constants.RULES); // Display the Rules from Constants
                handleHelpCommand();         // Display the Help Menu
                // ------------------------

                String joinMsg = String.format("%s [%s] [SERVER]: %s has joined the chat!%s", 
                                    Constants.YELLOW, getTime(), username, Constants.RESET);
                TermServer.broadcast(joinMsg);
            }

            // 2. Main Message Loop
            String msg;
            while ((msg = in.readLine()) != null) {
                // Apply word filter to every incoming message
                String filteredMsg = filterMessage(msg);

                if (filteredMsg.equalsIgnoreCase("/help")) {
                    handleHelpCommand();
                } else if (filteredMsg.startsWith("/msg ")) {
                    handlePrivateMessage(filteredMsg);
                } else if (filteredMsg.equalsIgnoreCase("/list")) {
                    handleListCommand();
                } else if (filteredMsg.equalsIgnoreCase("/quit")) {
                    break;
                } else {
                    // Standard Public Message
                    String time = getTime();
                    String formattedMsg = String.format("%s[%s] [%s]: %s%s", 
                                            Constants.GREEN, time, username, Constants.RESET, filteredMsg);
                    TermServer.broadcast(formattedMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("Connection lost with user: " + username);
        } finally {
            disconnect();
        }
    }

    private void handleHelpCommand() {
        String time = getTime();
        out.println(Constants.CYAN + "\n--- [" + time + "] TermChat Help Menu ---");
        out.println("/list                - See who is online");
        out.println("/msg <name> <text>   - Send a private message");
        out.println("/help                - Show this menu");
        out.println("/quit                - Exit the chat");
        out.println("---------------------------------------" + Constants.RESET);
    }

    private void handleListCommand() {
        Set<String> users = TermServer.clientMap.keySet();
        out.println(Constants.CYAN + "[" + getTime() + "] [SERVER]: Online users: " + users.toString() + Constants.RESET);
    }

    private void handlePrivateMessage(String rawMsg) {
        String[] parts = rawMsg.split(" ", 3);
        if (parts.length >= 3) {
            String target = parts[1];
            String message = parts[2];
            PrintWriter targetWriter = TermServer.clientMap.get(target);

            if (targetWriter != null) {
                String time = getTime();
                String logEntry = String.format("(Whisper: %s -> %s): %s", username, target, message);
                
                // Send to Target and Sender
                targetWriter.println(Constants.PURPLE + "[" + time + "] (Whisper from " + username + "): " + Constants.RESET + message);
                out.println(Constants.PURPLE + "[" + time + "] (Whisper to " + target + "): " + Constants.RESET + message);
                
                // Save to Server Log
                TermServer.logToFile("[" + time + "] " + logEntry);
            } else {
                out.println(Constants.RED + "[" + getTime() + "] [Error]: User '" + target + "' not found." + Constants.RESET);
            }
        } else {
            out.println(Constants.YELLOW + "[Usage]: /msg <username> <message>" + Constants.RESET);
        }
    }

    private void disconnect() {
        if (username != null) {
            TermServer.clientMap.remove(username);
            String leaveMsg = String.format("%s [%s] [SERVER]: %s has left the chat.%s", 
                                    Constants.YELLOW, getTime(), username, Constants.RESET);
            TermServer.broadcast(leaveMsg);
        }
        try {
            socket.close();
        } catch (IOException e) {
            // Socket already closed
        }
    }
}