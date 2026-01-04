package com.termchat.client;

import java.io.*;
import java.net.*;

public class MessageReceiver extends Thread {
    private BufferedReader in;

    public MessageReceiver(Socket socket) {
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                // \r clears the current line so the prompt stays at the bottom
                System.out.print("\r" + msg + "\n> "); 
            }
        } catch (IOException e) {
            System.out.println("Connection lost.");
        }
    }
}