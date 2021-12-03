package com.mattmx.tartarus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Authentication {
    public static String TOKEN = "";
    public static String USERNAME = "";
    public static String ip = "127.0.0.1";
    public static int port = 12;

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public enum State {
        LOGIN_SUCCESSFUL,
        LOGIN_FAILURE,
        SERVER_UNREACHABLE
    }

    public static State attemptLogin(String username, String password) {
        if (pingServer()) {
            send("USERNAME:'" + username + "'PASSWORD:'" + password + "'");
            String tkn = getResponse();
            if (tkn != null) {
                USERNAME = username;
                TOKEN = tkn;
                return State.LOGIN_SUCCESSFUL;
            }
            return State.LOGIN_FAILURE;
        }
        return State.SERVER_UNREACHABLE;
    }

    public static boolean pingServer() {
        try {
            startConnection();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private static void startConnection() throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private static void send(String s) {
        out.println(s);
    }

    private static String getResponse() {
        try {
            if (in.ready()) {
                return in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Could not read from datastream - Auth servers may be unavailable.";
    }
}
