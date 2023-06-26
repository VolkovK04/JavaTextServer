package ru.textserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Server {
    private final int port;
    public Server(int port) {
        this.port = port;
    }
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final ArrayList<ClientManager> clientManagers = new ArrayList<>();
    public void start() {
        try(ServerSocket server = new ServerSocket(port)){
            System.out.println("Server successfully started");
            while(!server.isClosed()){
                Socket client = server.accept();

                ClientManager clientManager = new ClientManager(client);
                System.out.println(clientManager + " connected");

                clientManager.addMessageListener(e -> OnMessage((ClientManager) e.getSource(), e.getMessage()));

                clientManagers.add(clientManager);

                threadPool.submit(() -> {
                    try {
                        clientManager.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendAll(String message) throws IOException {
        for (ClientManager clientManager: clientManagers) {
            clientManager.send(message);
        }
    }
    public abstract void OnMessage(ClientManager clientManager, String message) throws IOException;
}
