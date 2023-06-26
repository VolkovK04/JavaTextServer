package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager {
    private final Socket client;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final ArrayList<MessageListener> messageListeners = new ArrayList<>();
    public ClientManager(Socket client) throws IOException {
        this.client = client;
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    public String toString(){
        return client.getInetAddress().getHostAddress() + ":" + client.getPort();
    }
    public void start() throws IOException {
        while(!client.isClosed()) {
            String message = reader.readLine();
            if (message == null)
                continue;
            System.out.println(this + ": " + message);
            notifyMessageListeners(message);
        }
        System.out.println(this + " disconnect");
    }

    public void close() throws IOException {
        client.close();
        reader.close();
        writer.close();
    }
    public void send(String message) throws IOException {
        //System.out.println("Sending message to client");
        if (client.isClosed()){
            System.out.println(this + " disconnect");
            close();
            return;
        }
        writer.write(message + "\n");
        writer.flush();
    }
    private void notifyMessageListeners(String message) throws IOException {
        for (MessageListener listener : messageListeners) {
            listener.onMessage(new MessageEvent(this, message));
        }
    }
    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }
    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }
}
