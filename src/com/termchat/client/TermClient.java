package com.termchat.client;

import com.termchat.shared.Constants;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TermClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", Constants.PORT);
            System.out.println(Constants.CYAN + Constants.LOGO + Constants.RESET);
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Username: ");
            String name = scanner.nextLine();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(name); // Send name to server first

            new MessageReceiver(socket).start();

            System.out.println("Commands: /msg <name> <text> for private chat.");
            while (true) {
                String input = scanner.nextLine();
                out.println(input);
            }
        } catch (IOException e) {
            System.err.println("Could not connect to Server.");
        }
    }
}