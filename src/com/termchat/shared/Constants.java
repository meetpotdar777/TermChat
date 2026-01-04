package com.termchat.shared;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final int PORT = 5000;
    
    // ANSI Colors
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";
    
    // Word Filter List
    public static final List<String> BANNED_WORDS = Arrays.asList("badword1", "badword2", "spam", "spoiler");

    public static final String LOGO = 
        " _____                      _____ _           _ \n" +
        "|_   _|__ _ __ _ __ ___    /  __ | |__  __ _| |_\n" +
        "  | |/ _ \\ '__| '_ ` _ \\   | |   | '_ \\/ _` |  _|\n" +
        "  | |  __/ |  | | | | | |  | \\__ | | | \\__,_|\\__|\n" +
        "  |_|\\___|_|  |_| |_| |_|   \\____/_| |_|\\__,_|\\__|";

    // New Rules and Welcome message
    public static final String RULES = 
        "\n" + RED + "---------- CHAT RULES ----------\n" + RESET +
        "1. No spamming the terminal.\n" +
        "2. Private messages (/msg) are logged by the server.\n" +
        "3. Profanity is automatically filtered.\n" +
        RED + "--------------------------------" + RESET;
}