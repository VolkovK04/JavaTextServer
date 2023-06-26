package ru.textserver;

import java.io.IOException;

public class MyServer extends  Server{
    public MyServer(int port) {
        super(port);
    }

    @Override
    public void OnMessage(ClientManager clientManager, String message) throws IOException {
        sendAll(clientManager + ": " + message);
    }


}
